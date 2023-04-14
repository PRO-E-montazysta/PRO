import { useMemo } from 'react'
import useBreakpoints from './useBreakpoints'

export const useInputWidth = () => {
    const appSize = useBreakpoints()

    const inputWidth = useMemo(() => {
        switch (appSize.active) {
            case 'mobile':
                return '100%'
            case 'tablet':
                return 'calc(50% - 7.5px)'
            case 'notebook':
                    return 'calc(33.33% - 10px)'
            default:
                return 'calc(25% - calc(45px/4))'
        }
    }, [appSize])

    return inputWidth
}
