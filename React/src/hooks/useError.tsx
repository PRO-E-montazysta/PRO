import { Box } from '@mui/material'
import { useContext } from 'react'
import { DialogGlobalContext } from '../providers/DialogGlobalProvider'

const useError = () => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = (error: any) => {
        console.error(error)
        showDialog({
            btnOptions: [
                {
                    text: 'OK',
                    value: 0,
                },
            ],
            title: 'Błąd!',
            content: (
                <Box>
                    {error.response && process.env.NODE_ENV === 'development'
                        ? JSON.stringify(error.response.data)
                        : 'Wewnętrzny błąd serwera. Skontaktuj się z administratorem'}
                </Box>
            ),
        })
    }

    return showError
}
export default useError
