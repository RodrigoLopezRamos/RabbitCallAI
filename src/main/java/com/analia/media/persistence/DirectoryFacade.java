package com.analia.media.persistence;

import com.analia.common.model.Directory;
import com.analia.common.persistence.JPAPersistenceFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.QueryHint;


@NamedQuery(name = "getAllFileByDirectoryIdAndUserId",
        query = "       select f\n" +
                "        from File f,\n" +
                "        Directory d\n" +
                "        where\n" +
                "        (f.directoryId = d.id)\n" +
                "        and (d.id = :directoryId)\n" +
                "        and ((d.ownerId IS NULL) or (d.ownerId = :userId))\n" +
                "        order by f.secuenceNumber ASC",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@NamedQuery(name = "getFileByOwnerId",
        query = "        select f\n" +
                "        from File f,\n" +
                "        Directory d\n" +
                "        where\n" +
                "        (f.id = :fileId)\n" +
                "        and (f.directoryId = d.id)\n" +
                "        and ((d.ownerId IS NULL) or (d.ownerId = :userId))",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))

@NamedQuery(name = "getFileByName",
        query = "     select f\n" +
                "        from File f,\n" +
                "        Directory d\n" +
                "        where\n" +
                "        (f.directoryId = :directoryId)\n" +
                "        and(f.name =:name)\n" +
                "        and (f.directoryId = d.id)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@NamedQuery(name = "getFileWithDirectoryIdAndIndex",
        query = "   select f\n" +
                "        from File f\n" +
                "        where(f.directoryId=:directoryId)\n" +
                "        and(f.secuenceNumber=:sequenceNumber)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@ApplicationScoped
public class DirectoryFacade extends JPAPersistenceFacade<Directory> implements DirectoryFacadeLocal {

    @Inject
    private EntityManager entityManager;

    public DirectoryFacade() {
        super(Directory.class);
    }



}
