import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getToolDetails } from '../api/tool.api'
import { Tool } from '../types/model/Tool'
import { getElementDetails } from '../api/element.api'
import { Element } from '../types/model/Element'

export const DeletedToolName = (id: string) => {
    const deletedToolQuery = useQuery<Tool, AxiosError>(['deleted-tool'], async () => getToolDetails(id))
    return deletedToolQuery.data?.name! + ' - ' + deletedToolQuery.data?.code!
}

export const DeletedElementName = (id: string) => {
    const deletedElementQuery = useQuery<Element, AxiosError>(['deleted-element'], async () => getElementDetails(id))
    return deletedElementQuery.data?.name! + ' - ' + deletedElementQuery.data?.code!
}
