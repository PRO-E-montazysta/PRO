import { MenuElements } from "./NavBox"

export const changeMenuDecision = (navbarWidth: number, thisMenuElements: MenuElements): 0 | 1 | 2 => {
    if (navbarWidth && thisMenuElements) {
        //padding
        let s = thisMenuElements.scrollMenu.length > 0 ? 30 : 5
        thisMenuElements.navbar.forEach((d) => (s += d.width))
        const lastFromScrollMenuWidth = thisMenuElements.scrollMenu.at(-1)?.width
        if (s > navbarWidth) return 1
        else if (lastFromScrollMenuWidth && s + lastFromScrollMenuWidth < navbarWidth) return 2
        else return 0
    }
    return 0
}

export const navToScroll = (thisMenuElements: MenuElements): MenuElements | null => {
    if (thisMenuElements.navbar.length == 0) return null

    let nav = thisMenuElements.navbar
    let poped = nav.pop()
    if (!poped) return null
    let scroll = thisMenuElements.scrollMenu
    scroll.push(poped)
    const newMenuElements = {
        navbar: nav,
        scrollMenu: scroll,
    }
    return newMenuElements
}
export const scrollToNav = (thisMenuElements: MenuElements) => {
    if (thisMenuElements.scrollMenu.length == 0) return null
    let scroll = thisMenuElements.scrollMenu
    let poped = scroll.pop()
    if (!poped) return null
    let nav = thisMenuElements.navbar
    nav.push(poped)
    const newMenuElements = {
        navbar: nav,
        scrollMenu: scroll,
    }
    return newMenuElements
}


export const findChildWidthById = (el: HTMLElement | null, id: string): number => {
    let result = 3
    if (el)
        for (let i = 0; i < el.children.length; i++) {
            const target = el.children.item(i)
            if (target && target.id == id) result = target.clientWidth
        }
    return result
}
