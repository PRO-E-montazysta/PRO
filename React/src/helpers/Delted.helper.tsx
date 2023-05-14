import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getToolDetails } from '../api/tool.api'
import { Tool } from '../types/model/Tool'

export const DeletedToolName = (id: string) => {
    const toolQuery = useQuery<Tool, AxiosError>(['tool'], async () => getToolDetails(id))
    return toolQuery.data?.name! + ' - ' + toolQuery.data?.code!
}
