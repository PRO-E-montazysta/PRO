import { addAttachments, deleteAttachment } from '../../api/attachment.api'
import { v4 as uuidv4 } from 'uuid'

export type FileWithUuid = {
    file: File
    uuid: string
}

export const saveNewFiles = async (fileList: FileWithUuid[], stageId?: number) => {
    let isSuccess = true
    const promises = []
    for (let i = 0; i < fileList.length; i++) {
        const f = fileList.at(i)!
        const fd = new FormData()

        fd.append('file', f.file, f.file.name)
        fd.append(
            'attachmentDto',
            new Blob(
                [
                    JSON.stringify({
                        name: f.file.name,
                        typeOfAttachment: f.file.type == 'jpg' || f.file.type == 'png' ? 'ORDER_STAGE_PHOTO' : 'DESIGN',
                        orderStageId: stageId,
                        url: 'string',
                        description: 'string',
                        toolTypeId: null,
                        commentId: null,
                        employeeId: null,
                        toolEventId: null,
                        orderId: null,
                        elementId: null,
                        elementEventId: null,
                    }),
                ],
                {
                    type: 'application/json',
                },
            ),
        )
        promises.push(
            addAttachments(fd).catch((err) => {
                console.error(err)
                isSuccess = false
            }),
        )
    }
    await Promise.all(promises)
    return isSuccess
}
export const deleteFilesFromServer = async (fileIdsFromServerToDelete: number[]): Promise<boolean> => {
    let isSuccess = true
    const promises = []
    for (let i = 0; i < fileIdsFromServerToDelete.length; i++) {
        const attachemntIdToDelete = fileIdsFromServerToDelete.at(i)!
        promises.push(
            deleteAttachment(attachemntIdToDelete.toString()).catch((err) => {
                console.error(err)
                isSuccess = false
            }),
        )
    }
    await Promise.all(promises)
    return isSuccess
}

export const addUUIDToFileList = (fileList: FileList) => {
    const files = fileList ? Array.prototype.slice.call(fileList) : []
    let invalidateFileSizeArr: string[] = []
    const filesWithUuid: FileWithUuid[] = files
        .filter((f) => {
            //10mb
            if (f.size > 10000000) {
                invalidateFileSizeArr.push(f.name)
                return false
            }
            return true
        })
        .map((f) => {
            return {
                file: f,
                uuid: uuidv4(),
            }
        })
    return { filesWithUuid: filesWithUuid, invalidateFileSizeArr: invalidateFileSizeArr }
}
