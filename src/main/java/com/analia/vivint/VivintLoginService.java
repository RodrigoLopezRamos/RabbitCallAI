package com.analia.vivint;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class VivintLoginService {

    private final Browser browser;

    @ConfigProperty(name = "vivint.login.url", defaultValue = "https://signin.vivint.com")
    String loginUrl;

    @ConfigProperty(name = "playwright.navigation.timeout", defaultValue = "30000")
    int navigationTimeout;

    @ConfigProperty(name = "download.directory", defaultValue = "./downloads")
    String downloadDirectory;

    @Inject
    public VivintLoginService(Browser browser) {
        this.browser = browser;
    }

    /**
     * Login to Vivint Okta portal and download CSV
     *
     * @param username Username for login
     * @param password Password for login
     * @return Path to downloaded CSV file or null if download failed
     */
    public Path loginAndDownloadCsv(String username, String password) {
        try (BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 800)
                .setAcceptDownloads(true)
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36"))) {

            Page page = context.newPage();
            page.setDefaultNavigationTimeout(navigationTimeout);

            // Navigate to login page
            Log.info("Navigating to Vivint login page: " + loginUrl);
            page.navigate(loginUrl);

            // Login sequence
            try {
                Log.info("Waiting for username field");
                page.waitForSelector("input[name='identifier']",
                        new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

                // Fill the username field
                Log.info("Entering username: " + maskUsername(username));
                page.fill("input[name='identifier']", username);
                page.waitForTimeout(1000);

                // Click Next button
                Log.info("Clicking Next button");
                page.click("text=Next");

                // Wait for password field
                Log.info("Waiting for password field");
                page.waitForSelector("input[name='credentials.passcode']",
                        new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

                // Fill password field
                Log.info("Entering password");
                page.fill("input[name='credentials.passcode']", password);

                // Click Verify button
                Log.info("Clicking Verify button");
                page.click("input.button.button-primary[type='submit'][value='Verify']");

                // Wait for login to complete and redirect to opportunities page
                Log.info("Waiting for navigation to opportunities page");
                page.waitForURL("**/opportunities", new Page.WaitForURLOptions().setTimeout(30000));
                Log.info("Successfully navigated to opportunities page");

                // Ensure page is fully loaded
                page.waitForLoadState(LoadState.NETWORKIDLE);

                // Wait for any potential overlays or loaders to disappear
                try {
                    page.waitForSelector("div.loading-overlay",
                            new Page.WaitForSelectorOptions()
                                    .setState(WaitForSelectorState.HIDDEN)
                                    .setTimeout(10000));
                } catch (Exception e) {
                    Log.debug("No loading overlay found or it disappeared quickly");
                }

                // Wait a bit to ensure UI is fully loaded and ready
                page.waitForTimeout(3000);

                // Generate timestamp for unique filename
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                Path downloadPath = Paths.get(downloadDirectory, "vivint_export_" + timestamp + ".csv");

                // Take screenshot before clicking Export
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(downloadDirectory, "before_export_" + timestamp + ".png")));

                // First find the Export button, then find the CSV button
                Log.info("Looking for Export button");

                // Specific button sequence: First Export, then CSV
                // Try to find the Export button with the exact selectors
                String exportSelector = "button.button.button-secondary:has-text('Export')";
                try {
                    Log.info("Waiting for Export button");
                    page.waitForSelector(exportSelector,
                            new Page.WaitForSelectorOptions()
                                    .setState(WaitForSelectorState.VISIBLE)
                                    .setTimeout(10000));

                    Log.info("Clicking Export button");
                    page.click(exportSelector);

                    // Wait a moment for the dropdown to appear
                    page.waitForTimeout(1000);

                    // Now look for the CSV button with the specific selector you provided
                    String csvSelector = "button.button.button-tertiary[data-v-30ae185c][data-v-e3f22068][data-v-2128691a]:has-text('CSV')";
                    Log.info("Waiting for CSV button with selector: " + csvSelector);

                    // First check if the button exists
                    if (page.locator(csvSelector).count() == 0) {
                        // Try a more general selector
                        csvSelector = "button.button-tertiary:has-text('CSV')";
                        Log.info("Specific selector not found, trying more general selector: " + csvSelector);

                        if (page.locator(csvSelector).count() == 0) {
                            // Try by text
                            csvSelector = "text=CSV";
                            Log.info("Still not found, trying by text: " + csvSelector);
                        }
                    }

                    // Take screenshot before clicking CSV
                    page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(downloadDirectory, "before_csv_" + timestamp + ".png")));

                    // Set up download listener and click the CSV button
                    String finalCsvSelector = csvSelector;
                    Download download = page.waitForDownload(() -> {
                        try {
                            Log.info("Clicking CSV button");
                            page.click(finalCsvSelector);
                        } catch (Exception e) {
                            Log.error("Error clicking CSV button with selector: " + finalCsvSelector, e);

                            // Try alternative selector
                            try {
                                Log.info("Trying alternative approach - JavaScript");
                                page.evaluate("() => {\n" +
                                        "  const buttons = Array.from(document.querySelectorAll('button'));\n" +
                                        "  const csvButton = buttons.find(b => b.textContent.includes('CSV'));\n" +
                                        "  if (csvButton) {\n" +
                                        "    console.log('Found CSV button, clicking...');\n" +
                                        "    csvButton.click();\n" +
                                        "    return true;\n" +
                                        "  }\n" +
                                        "  console.log('CSV button not found');\n" +
                                        "  return false;\n" +
                                        "}");
                            } catch (Exception jsError) {
                                Log.error("JavaScript click also failed", jsError);
                            }
                        }
                    });

                    // Save the downloaded file
                    Log.info("Download started, saving file to: " + downloadPath);
                    download.saveAs(downloadPath);
                    Log.info("CSV file downloaded successfully to: " + downloadPath.toAbsolutePath());

                    return downloadPath;

                } catch (Exception e) {
                    Log.error("Error in export/CSV button sequence", e);

                    // Take error screenshot
                    page.screenshot(new Page.ScreenshotOptions()
                            .setPath(Paths.get(downloadDirectory, "error_export_" + timestamp + ".png"))
                            .setFullPage(true));

                    Log.info("Trying direct CSV button approach");
                    try {
                        // Try to find a CSV button directly
                        String directCsvSelector = "button:has-text('CSV')";
                        if (page.locator(directCsvSelector).count() > 0) {
                            Download download = page.waitForDownload(() -> {
                                page.click(directCsvSelector);
                            });

                            download.saveAs(downloadPath);
                            Log.info("CSV downloaded with direct approach");
                            return downloadPath;
                        }
                    } catch (Exception directCsvError) {
                        Log.error("Direct CSV button approach failed", directCsvError);
                    }

                    // If we reached here, all attempts failed
                    return null;
                }

            } catch (Exception e) {
                Log.error("Error during login or download process", e);
                // Take screenshot on error
                try {
                    String errorTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                    page.screenshot(new Page.ScreenshotOptions()
                            .setPath(Paths.get(downloadDirectory, "error_" + errorTimestamp + ".png"))
                            .setFullPage(true));
                    Log.info("Error screenshot saved to: " + Paths.get(downloadDirectory, "error_" + errorTimestamp + ".png"));
                } catch (Exception screenshotError) {
                    Log.error("Failed to take error screenshot", screenshotError);
                }
                return null;
            }
        }
    }

    /**
     * Mask username for logging purposes
     */
    private String maskUsername(String username) {
        if (username == null || username.length() <= 4) {
            return "****";
        }

        int showChars = Math.min(2, username.length());
        int maskLength = username.length() - showChars;
        StringBuilder masked = new StringBuilder();
        masked.append(username.substring(0, showChars));
        for (int i = 0; i < maskLength; i++) {
            masked.append("*");
        }
        return masked.toString();
    }
}
