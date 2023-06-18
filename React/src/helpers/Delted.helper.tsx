import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getToolDetails } from '../api/tool.api'
import { Tool } from '../types/model/Tool'
import { getElementDetails } from '../api/element.api'
import { Element } from '../types/model/Element'
import { Client } from '../types/model/Client'
import { getClientDetails } from '../api/client.api'

export const DeletedToolName = (id: string) => {
    const deletedToolQuery = useQuery<Tool, AxiosError>(['deleted-tool', { id: id }], async () => getToolDetails(id))
    return deletedToolQuery.data?.name! + ' - ' + deletedToolQuery.data?.code!
}

export const DeletedElementName = (id: string) => {
    const deletedElementQuery = useQuery<Element, AxiosError>(['deleted-element', { id: id }], async () =>
        getElementDetails(id),
    )
    return deletedElementQuery.data?.name! + ' - ' + deletedElementQuery.data?.code!
}

export const DeletedClientName = (id: string) => {
    const deletedClientQuery = useQuery<Client, AxiosError>(['deleted-client', { id: id }], async () =>
        getClientDetails(id),
    )
    return deletedClientQuery.data?.name!
}
