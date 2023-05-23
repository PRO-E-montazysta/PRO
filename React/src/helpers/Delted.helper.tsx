import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getToolDetails } from '../api/tool.api'
import { Tool } from '../types/model/Tool'
import { getElementDetails } from '../api/element.api'
import { Element } from '../types/model/Element'

export const DeletedToolName = (id: string) => {
    const toolQuery = useQuery<Tool, AxiosError>(['deleted-tool'], async () => getToolDetails(id))
    return toolQuery.data?.name! + ' - ' + toolQuery.data?.code!
}

export const DeletedElementName = (id: string) => {
    const toolQuery = useQuery<Element, AxiosError>(['deleted-element'], async () => getElementDetails(id))
    return toolQuery.data?.name! + ' - ' + toolQuery.data?.code!
}
