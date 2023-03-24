import useAppWidth from './useAppWidth'

export enum AppSize {
    mobile = 'mobile',
    tablet = 'tablet',
    notebook = 'notebook',
    desktop = 'desktop',
}

//480, 768. 1024,
export default function useBreakpoints() {
    const breakpoints = {
        isMobile: useAppWidth('(max-width: 480px)'),
        isTablet: useAppWidth('(min-width: 481px) and (max-width: 768px)'),
        isNotebook: useAppWidth('(min-width: 769px) and (max-width: 1024px)'),
        isDesktop: useAppWidth('(min-width: 1025px)'),
        active: 'desktop',
    }
    if (breakpoints.isMobile) breakpoints.active = 'mobile'
    if (breakpoints.isTablet) breakpoints.active = 'tablet'
    if (breakpoints.isNotebook) breakpoints.active = 'notebook'
    if (breakpoints.isDesktop) breakpoints.active = 'desktop'
    console.log(breakpoints)
    return breakpoints
}
