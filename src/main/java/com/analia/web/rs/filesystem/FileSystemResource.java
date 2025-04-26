package com.analia.web.rs.filesystem;

import com.analia.common.constants.Constants;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.File;
import com.analia.common.model.UserDirectory;
import com.analia.common.util.Base26;
import com.analia.common.util.SettingsUtil;
import com.analia.media.core.FileSystemCore.FileType;
import com.analia.media.service.FileSystemServiceLocal;
import com.analia.media.util.FileSystemUtils;
import com.analia.user.service.UserService;
import com.analia.web.util.MimeTypeUtil.MimeType;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartInput;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;

//"/rs/fileSystemResource/profilePictureUpload.a
@Path("/fileSystemResource")
@ApplicationScoped
public class FileSystemResource {
    @Inject
    private FileSystemServiceLocal fileSystemServiceLocal;

    @Inject
    private UserService userServiceLocal;

    /**
     * @param input
     * @return
     * @throws IOException
     */
    @POST
    @Path("/upload.a")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response uploadFile(MultipartInput input) throws IOException {
        Response response = null;
        try {
            Collection<InputPart> inputs = input.getParts();
            processRequest(inputs);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @param input
     * @return
     * @throws IOException
     */
    @POST
    @Path("/profilePictureUpload.a")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response profilePictureUpload(MultipartInput input) throws IOException {
        Response response = null;
        try {
            processProfilePictureUploadRequest(input.getParts());
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    @POST
    @Path("/imageUpload/{directory_id}/{sequence}/{name}/.n")
    @Consumes("*/*")
    public Response uploadImageFile(@PathParam("name") String name, @PathParam("directory_id") int directoryId, @PathParam("sequence") int secuence, @MultipartForm FileUploadForm form) throws AnaliaException, IOException {
        Response response = null;
        byte[] bytes = form.getFileData();

        String pathToUpload = directoryId + "/" + name;
        FileSystemUtils.writeAndResizeFile(bytes, pathToUpload, SettingsUtil.getMediaFolder());

        File file = new File();
        file.setDirectoryId(BigInteger.valueOf(directoryId));

        file.setName(name);
        file.setPath(pathToUpload);

        fileSystemServiceLocal.saveFile(file.getId(), FileType.IMAGE, file.getDirectoryId(), secuence, file.getName(), file.getDescription(), pathToUpload, MimeType.JPEGIMAGE.getMimeType());

        response = Response.ok(ResponseUtils.createSucessResponse()).build();
        return response;
    }


    /**
     * @param inputs
     * @throws IOException
     * @throws AnaliaException
     */
    private void processRequest(Collection<InputPart> inputs) throws IOException, AnaliaException {
        RequestUtils.validateNotNull("inputs", inputs);
        BigInteger directoryId = BigInteger.valueOf(0);
        int index = 0;
        for (InputPart part : inputs) {
            String mediaType = part.getMediaType().toString();
            if (mediaType.contains("text/plain")) {
                directoryId = Base26.decode(part.getBodyAsString());
            } else if (mediaType.contains("image")) {
                if (directoryId == null) {
                    throw new AnaliaException(ExceptionCode.INVALID_INPUT_VALUES, "directoryId is not valid");
                }

                InputStream inputStream = part.getBody(InputStream.class, null);
                MultivaluedMap<String, String> header = part.getHeaders();
                String uploadedFileName = FileSystemUtils.getFileName(header);
                userServiceLocal.getUserDirectory(directoryId);
                File oldFile = fileSystemServiceLocal.getFile(uploadedFileName, directoryId);
                File file = oldFile != null ? oldFile : new File();
                file.setDirectoryId(directoryId);


                byte[] bytes = FileSystemUtils.convertInputToByteArray(inputStream);
                String pathToUpload = file.getDirectoryId() + "/" + uploadedFileName;
                FileSystemUtils.writeAndResizeFile(bytes, pathToUpload, SettingsUtil.getMediaFolder());

                file.setName(uploadedFileName);
                file.setPath(pathToUpload);

                //int mimeType = MimeTypeUtil.getMimeTypeIdForString(mediaType);
                fileSystemServiceLocal.saveFile(file.getId(), FileType.IMAGE, file.getDirectoryId(), index, file.getName(), file.getDescription(), pathToUpload, mediaType);
                index++;
            }

        }
    }

    private File processProfilePictureUploadRequest(Collection<InputPart> inputs) throws AnaliaException, IOException {
        File file = null;
        for (InputPart part : inputs) {
            String mediaType = part.getMediaType().toString();
            if (mediaType.contains("image")) {
                InputStream inputStream = part.getBody(InputStream.class, null);
                byte[] bytes = FileSystemUtils.convertInputToByteArray(inputStream);
                Calendar calendar = Calendar.getInstance();
                String uploadedFileName = calendar.getTimeInMillis() + Constants.USER_PROFILE_PICTURE + ".jpg";
                UserDirectory userDirectory = userServiceLocal.getUserDirectory();
                String path = userDirectory.getDirectoryId() + "/" + uploadedFileName;
                FileSystemUtils.writeAndResizeFile(bytes, path, SettingsUtil.getMediaFolder());

                file = fileSystemServiceLocal.getFileWithDirectoryIdAndIndex(userDirectory.getDirectoryId(), Constants.SEQUENCE_NUMBER_CONSTANT);
                if (file == null) {
                    file = fileSystemServiceLocal.saveFile(BigInteger.valueOf(Constants.NEW_INSTANCE_ID), FileType.IMAGE, userDirectory.getDirectoryId(),0, uploadedFileName, uploadedFileName, path, MimeType.JPEGIMAGE.getMimeType());
                }
                file.setPath(path);
                file.setName(uploadedFileName);
                fileSystemServiceLocal.saveFile(file);
                return file;
            }
        }
        throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, "Profile Image not found in request multiparts");
    }

}
