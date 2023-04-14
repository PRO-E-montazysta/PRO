import { createContext, ReactNode, useState } from 'react'
import DialogGlobal, { DialogGlobalParams } from '../components/dialogGlobal/DialogGlobal'

const DialogGlobalContext = createContext({
    showDialog: (params: DialogGlobalParams) => {},
})

const DialogGlobalProvider = ({ children }: any) => {
    const [isOpen, setIsOpen] = useState(false)
    const [dialog, setDialog] = useState<DialogGlobalParams>({
        btnOptions: [],
        callback: () => {},
        content: undefined,
        title: '',
    })

    const showDialog = (params: DialogGlobalParams) => {
        setIsOpen(true)
        setDialog({
            ...params,
            callback: (response: number) => {
                setIsOpen(false)
                if (params.callback) params.callback(response)
            },
        })
    }

    return (
        <DialogGlobalContext.Provider value={{ showDialog }}>
            {children}
            {isOpen && <DialogGlobal {...dialog} />}
        </DialogGlobalContext.Provider>
    )
}

export { DialogGlobalContext, DialogGlobalProvider }
