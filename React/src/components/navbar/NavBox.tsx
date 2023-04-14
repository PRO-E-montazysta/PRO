import React, { Ref, useEffect, useLayoutEffect, useRef, useState } from 'react'
import { Box } from '@mui/material'
import { pageList, PageProps } from '../../utils/pageList'
import NavMenuButton from '../navbar/NavButton'
import { isAuthorized } from '../../utils/authorize'
import { width } from '@mui/system'
import MorePagesButton from './MorePagesButton'

export type NavBoxProps = {}

type MenuElements = {
    navbar: PageElement[]
    scrollMenu: PageElement[]
}
type PageElement = {
    width: number
    page: PageProps
}

const NavBox = () => {
    const rootPage = pageList.find((p) => p.path === '/')
    const availablePages = rootPage?.children?.filter((c) => c.inNav && isAuthorized(c))
    const initNavbar = availablePages?.map((p) => {
        return {
            page: p,
            width: 200,
        }
    })

    const menuRef = useRef<HTMLElement>(null)
    const [menuElements, setMenuElements] = useState<MenuElements>({
        navbar: initNavbar ? initNavbar : [],
        scrollMenu: [],
    })

    useEffect(() => {
        const handleResize = () => {
            const decision = changeMenuDecision()
            if (decision == 1) navToScroll()
            if (decision == 2) scrollToNav()
        }
        window.addEventListener('resize', handleResize)
        handleResize()
        return () => window.removeEventListener('resize', handleResize)
    }, [menuElements])

    const findChildWidthById = (el: HTMLElement | null, id: string): number => {
        let result = 200
        if (el)
            for (let i = 0; i < el.children.length; i++) {
                const target = el.children.item(i)
                if (target && target.id == id) result = target.clientWidth
            }
        return result
    }

    useLayoutEffect(() => {
        if (menuRef.current && availablePages) {
            const newMenuElements: PageElement[] = []
            availablePages.forEach((ap) => {
                const width = findChildWidthById(menuRef.current, ap.path)
                const newPageElement: PageElement = {
                    page: ap,
                    width: width,
                }
                newMenuElements.push(newPageElement)
            })
            setMenuElements({ navbar: newMenuElements, scrollMenu: [] })
        }
    }, [])

    const changeMenuDecision = (): 0 | 1 | 2 => {
        if (menuRef.current && menuElements) {
            //padding
            let s = menuElements.scrollMenu.length > 0 ? 50 : 5
            menuElements.navbar.forEach((d) => (s += d.width))
            const offsetWidth = menuRef.current.clientWidth
            const lastFromScrollMenuWidth = menuElements.scrollMenu.at(-1)?.width
            if (s > offsetWidth) return 1
            else if (lastFromScrollMenuWidth && s + lastFromScrollMenuWidth < offsetWidth) return 2
            else return 0
        }
        return 0
    }

    const navToScroll = () => {
        if (menuElements.navbar.length > 0) {
            let nav = menuElements.navbar
            let poped = nav.pop()
            if (poped) {
                let scroll = menuElements.scrollMenu
                scroll.push(poped)
                setMenuElements({
                    navbar: nav,
                    scrollMenu: scroll,
                })
            }
        }
    }
    const scrollToNav = () => {
        if (menuElements.scrollMenu.length > 0) {
            let scroll = menuElements.scrollMenu
            let poped = scroll.pop()
            if (poped) {
                let nav = menuElements.navbar
                nav.push(poped)
                setMenuElements({
                    navbar: nav,
                    scrollMenu: scroll,
                })
            }
        }
    }

    return (
        <Box
            ref={menuRef}
            display={'flex'}
            sx={{
                overflow: 'hidden',
                alignItems: 'center',
                width: '100%',
            }}
        >
            {menuElements.navbar
                ? menuElements.navbar.map((element, index) => {
                      return <NavMenuButton {...element.page} key={index} />
                  })
                : null}
            {menuElements.scrollMenu.length > 0 && (
                <MorePagesButton pages={menuElements.scrollMenu.map((s) => s.page)} />
            )}
        </Box>
    )
}

export default NavBox
