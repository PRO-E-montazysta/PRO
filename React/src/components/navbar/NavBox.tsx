import React, { Ref, useEffect, useLayoutEffect, useRef, useState } from 'react'
import { Box } from '@mui/material'
import { pageList, PageProps } from '../../utils/pageList'
import NavMenuButton from '../navbar/NavButton'
import { isAuthorized } from '../../utils/authorize'
import { width } from '@mui/system'
import MorePagesButton from './MorePagesButton'
import { changeMenuDecision, findChildWidthById, navToScroll, scrollToNav } from './helper'

export type NavBoxProps = {}

export type MenuElements = {
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
    const [width, setWidth] = useState(0)
    const initNavbar = availablePages?.map((p) => {
        return {
            page: p,
            width: 2,
        }
    })

    const menuRef = useRef<HTMLElement>(null)
    const [menuElements, setMenuElements] = useState<MenuElements>({
        navbar: initNavbar ? initNavbar : [],
        scrollMenu: [],
    })


    const handleNavbarResize = (thisMenuElements: MenuElements) => {
        if (!menuRef.current) return
        const decision = changeMenuDecision(menuRef.current.clientWidth, thisMenuElements)
        if (decision == 1) {
            const newMenuElements = navToScroll(thisMenuElements)
            if (newMenuElements) {
                setMenuElements(newMenuElements)
                handleNavbarResize(newMenuElements)
            }
        } else if (decision == 2) {
            const newMenuElements = scrollToNav(thisMenuElements)
            if (newMenuElements) {
                setMenuElements(newMenuElements)
                handleNavbarResize(newMenuElements)
            }
        }
    }

    useEffect(() => {
        handleNavbarResize(menuElements)
    }, [width])


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
            handleNavbarResize({ navbar: newMenuElements, scrollMenu: [] })
        }
    }, [])

    useEffect(() => {
        const handleResize = () => {
            setWidth(window.innerWidth)
        }
        window.addEventListener('resize', handleResize)
        return () => window.removeEventListener('resize', handleResize)
    }, [])



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
