import { Button, Typography } from '@mui/material'
import './style.less'

import { ReactNode } from 'react'

export type DialogGlobalParams = {
    title: string
    content?: ReactNode
    callback?: (response: number) => void
    btnOptions: Array<DialogGlobalButton>
    under?: boolean
}

type DialogGlobalButton = {
    value: number
    text: string
    variant?: 'outlined' | 'contained' | 'text'
}

const DialogGlobal = (params: DialogGlobalParams) => {
    const { btnOptions, callback, content, title } = params

    const handleBtnClick = (value: number) => {
        if (callback) {
            callback(value)
        }
    }
    return (
        <div className="blur-background">
            <div className="global-dialog">
                <form>
                    <Typography variant="h5" sx={{ mb: '10px' }}>
                        {title}
                    </Typography>
                    {content}
                    <div className="action-buttons">
                        {btnOptions.map((opt, idx) => {
                            return (
                                <Button
                                    id={`dialogGlobal-${opt.text}`}
                                    color="secondary"
                                    key={idx}
                                    onClick={() => handleBtnClick(opt.value)}
                                    variant={opt.variant}
                                >
                                    {opt.text}
                                </Button>
                            )
                        })}
                    </div>
                </form>
            </div>
        </div>
    )
}

export default DialogGlobal
