import { useMemo } from 'react'
import useBreakpoints from './useBreakpoints'

export const useInputWidth = (thin?: boolean) => {
    const appSize = useBreakpoints()

    const inputWidth = useMemo(() => {
        if (thin)
            switch (appSize.active) {
                case 'mobile' || 'tablet':
                    return '100%'
                default:
                    return 'calc(50% - 7.5px)'
            }
        else
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
