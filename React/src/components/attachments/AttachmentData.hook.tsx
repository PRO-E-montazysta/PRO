import { ChangeEvent, useLayoutEffect, useState } from 'react'
import { Attachment } from '../../types/model/Attachment'
import { FileWithUuid, addUUIDToFileList } from './attachments.helper'
import { getAttachment } from '../../api/attachment.api'

type useAttachmentDataProps = {
    idsOfFilesFromServer: number[]
}

const useAttachmentData = (props: useAttachmentDataProps) => {
    const [fileListLocal, setFileListLocal] = useState<FileWithUuid[]>([])
    const [fileListFromServer, setFileListFromServer] = useState<Attachment[]>([])
    const [fileIdsFromServerToDelete, setFileIdsFromServerToDelete] = useState<number[]>([])

    useLayoutEffect(() => {
        if (props.idsOfFilesFromServer.length > 0) {
            const promises: Promise<Attachment>[] = []

            props.idsOfFilesFromServer.forEach((a) => {
                promises.push(getAttachment(a.toString()))
            })

            const filesFromServer: Attachment[] = []
            Promise.all(promises).then((arr) => {
                arr.sort((a, b) => (a.fileName > b.fileName ? 1 : -1)).forEach((r: Attachment) =>
                    filesFromServer.push(r),
                )
                setFileListFromServer(filesFromServer.filter((f) => f.deleted == false))
                setFileListLocal([])
            })
        } else setFileListLocal([])
    }, [props.idsOfFilesFromServer])

    const handleLocalFileDelete = (localUUID: string) => {
        const updatedFileList = fileListLocal.filter((file) => file.uuid !== localUUID)
        setFileListLocal(updatedFileList)
    }

    const handleFileFromServerDelete = (id: number) => {
        setFileIdsFromServerToDelete([...fileIdsFromServerToDelete, id])
    }

    const handleAddNewFiles = (fileList: FileList): string[] => {
        const { filesWithUuid, invalidateFileSizeArr } = addUUIDToFileList(fileList)

        if (filesWithUuid) setFileListLocal([...fileListLocal, ...filesWithUuid])
        return invalidateFileSizeArr
    }

    const handleReset = () => {
        setFileListLocal([])
        setFileIdsFromServerToDelete([])
    }

    return {
        fileListLocal: fileListLocal,
        fileListFromServer: fileListFromServer.filter((f) => fileIdsFromServerToDelete.indexOf(f.id) == -1),
        fileIdsFromServerToDelete: fileIdsFromServerToDelete,
        handleLocalFileDelete: handleLocalFileDelete,
        handleFileFromServerDelete: handleFileFromServerDelete,
        handleAddNewFiles: handleAddNewFiles,
        handleReset: handleReset,
    }
}

export default useAttachmentData
