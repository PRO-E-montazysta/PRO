import { Box, Button, CircularProgress, Typography } from '@mui/material'
import { ChangeEvent, useContext, useRef, useState } from 'react'
import DeleteIcon from '@mui/icons-material/Delete'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { FileWithUuid } from './attachments.helper'
import { Attachment } from '../../types/model/Attachment'
import { getBaseUrl } from '../../api/utils.api'

export type AttachmentsProps = {
    readonly?: boolean
    fileListLocal: FileWithUuid[]
    fileListFromServer: Attachment[]
    handleLocalFileDelete: (localUUID: string) => void
    handleFileFromServerDelete: (id: number) => void
    handleAddNewFiles: (fileList: FileList) => string[]
}

const Attachments = (props: AttachmentsProps) => {
    const {
        fileListLocal,
        fileListFromServer,
        readonly,
        handleAddNewFiles,
        handleFileFromServerDelete,
        handleLocalFileDelete,
    } = props
    const [isLoading, setIsLoading] = useState(false)
    const refToFileInput = useRef<HTMLInputElement>(null)
    const { showDialog } = useContext(DialogGlobalContext)

    const handleFileChange = async (e: ChangeEvent<HTMLInputElement>) => {
        setIsLoading(true)

        const thisFileList = e.target.files
        if (!thisFileList) {
            setIsLoading(false)
            return
        }
        const invalidateFileSizeArr = handleAddNewFiles(thisFileList)

        if (invalidateFileSizeArr.length > 0)
            showDialog({
                title: 'Zbyt duży rozmiar pliku',
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                content: `Następujące pliki mają za duży rozmiar: ${invalidateFileSizeArr.join(', ')}`,
            })
        setIsLoading(false)
    }

    const openFileSelector = () => {
        setIsLoading(true)
        if (refToFileInput.current) refToFileInput.current.click()
    }

    const downloadAttachment = (uniqueName: string, name: string) => {
        const link = getBaseUrl() + `/attachments/download/${uniqueName}`

        const tempLink = document.createElement('a')
        tempLink.href = link
        tempLink.setAttribute('download', name)
        tempLink.click()
    }

    return (
        <Box>
            <input
                style={{
                    display: 'none',
                }}
                ref={refToFileInput}
                type="file"
                onChange={handleFileChange}
                multiple
            />
            <ul>
                {fileListFromServer &&
                    fileListFromServer.map((file, i) => (
                        <li key={i} style={{ display: 'flex', marginBottom: '5px' }}>
                            <button
                                type="button"
                                style={{ background: 'none', border: 'none', padding: 0 }}
                                onClick={() => downloadAttachment(file.uniqueName, file.name)}
                            >
                                <Typography sx={{ color: 'black', textDecoration: 'underline', cursor: 'pointer' }}>
                                    {file.name}
                                </Typography>
                            </button>
                            {!readonly && (
                                <DeleteIcon
                                    onClick={() => handleFileFromServerDelete(file.id)}
                                    style={{
                                        cursor: 'pointer',
                                        marginLeft: '10px',
                                    }}
                                />
                            )}
                        </li>
                    ))}
                {fileListLocal &&
                    fileListLocal.map((file, i) => (
                        <li key={i} style={{ display: 'flex', marginBottom: '5px' }}>
                            {file.file.name}
                            {!readonly && (
                                <DeleteIcon
                                    onClick={() => handleLocalFileDelete(file.uuid)}
                                    style={{
                                        cursor: 'pointer',
                                        marginLeft: '10px',
                                    }}
                                />
                            )}
                        </li>
                    ))}
            </ul>
            {!readonly && (
                <>
                    <Button
                        sx={{ ml: '40px', position: 'absolute' }}
                        variant="contained"
                        onClick={openFileSelector}
                        endIcon={isLoading ? <CircularProgress size={20} sx={{ color: 'white' }} /> : null}
                    >
                        Dodaj załączniki
                    </Button>
                </>
            )}
        </Box>
    )
}

export default Attachments
