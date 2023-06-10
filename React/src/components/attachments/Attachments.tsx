import { Box, Button, CircularProgress } from '@mui/material'
import { ChangeEvent, useEffect, useRef, useState } from 'react'
import DeleteIcon from '@mui/icons-material/Delete'
import { v4 as uuidv4 } from 'uuid'
import { TypeOfAttachment } from '../../types/model/Attachment'
import { addAttachments } from '../../api/attachment.api'

type FileWithUuid = {
    file: File
    uuid: string
}

type AttachmentsProps = {
    stageId: number
    saveTrigger: boolean
}

const Attachments = (props: AttachmentsProps) => {
    const [isLoading, setIsLoading] = useState(false)
    const [fileList, setFileList] = useState<FileWithUuid[]>([])
    const [fileListFromServer, setFileListFromServer] = useState<File[]>()
    const refToFileInput = useRef<HTMLInputElement>(null)

    useEffect(() => {
        if (props.saveTrigger) saveFiles()
    }, [props.saveTrigger])

    const saveFiles = () => {
        fileList.forEach((f, i) => {
            const fd = new FormData()
            fd.append('file', f.file, f.file.name)
            fd.append(
                'attachmentDto',
                JSON.stringify({
                    name: f.file.name,
                    typeOfAttachment:
                        f.file.type == 'jpg' || f.file.type == 'png'
                            ? TypeOfAttachment.ORDER_STAGE_PHOTO
                            : TypeOfAttachment.DESIGN,
                    orderStageId: 5,
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
            )
            addAttachments(fd).then((d) => console.log(d))
        })
    }

    const handleFileChange = async (e: ChangeEvent<HTMLInputElement>) => {
        setIsLoading(true)
        const thisFileList = e.target.files
        const files = thisFileList ? Array.prototype.slice.call(thisFileList) : []

        const filesWithUuid: FileWithUuid[] = files.map((f) => {
            return {
                file: f,
                uuid: uuidv4(),
            }
        })
        if (e.target.files) {
            if (fileList) setFileList([...fileList, ...filesWithUuid])
            else setFileList(filesWithUuid)
        }
    }

    const onDelete = (id: string) => {
        if (fileList) {
            const updatedFileList = fileList.filter((file) => file.uuid !== id)
            setFileList(updatedFileList)
        }
    }
    useEffect(() => {
        console.log(fileList)
        setIsLoading(false)
    }, [fileList])
    useEffect(() => {
        console.log(isLoading)
    }, [isLoading])
    const handleButtonClick = () => {
        setIsLoading(true)
        if (refToFileInput.current) refToFileInput.current.click()
    }

    return (
        <Box>
            <Button
                variant="contained"
                onClick={handleButtonClick}
                endIcon={isLoading ? <CircularProgress size={20} sx={{ color: 'white' }} /> : null}
            >
                Dodaj załączniki
            </Button>
            <input
                style={{
                    display: 'none',
                }}
                ref={refToFileInput}
                className="upload"
                type="file"
                onChange={handleFileChange}
                multiple
            />
            <Button variant="contained" onClick={saveFiles}>
                ZAPISZ TO!
            </Button>
            <ul>
                {fileList &&
                    fileList.map((file, i) => (
                        <li key={i} style={{ display: 'flex', marginBottom: '5px' }}>
                            {file.file.name}
                            <DeleteIcon
                                onClick={() => onDelete(file.uuid)}
                                style={{
                                    cursor: 'pointer',
                                    marginLeft: '10px',
                                }}
                            />
                        </li>
                    ))}
            </ul>
        </Box>
    )
}

export default Attachments
