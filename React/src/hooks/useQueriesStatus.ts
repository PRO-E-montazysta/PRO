import { AxiosError } from 'axios'
import { UseMutationResult, UseQueryResult } from 'react-query'
import { useState, useEffect } from 'react'

export type QueriesStatusResult = {
    result: QueriesStatus
    errorMessage: string
    handleResetStatus: () => void
}
type QueriesStatus = 'isSuccess' | 'isError' | 'isLoading' | ''

export const useQueriesStatus = (
    queryList: Array<UseQueryResult<any, AxiosError>>,
    mutationList: Array<UseMutationResult<any, any, any, any>>,
): QueriesStatusResult => {
    const [result, setResult] = useState<QueriesStatus>('isLoading')
    const [message, setMessage] = useState('')

    const handleResetStatus = () => {
        mutationList.forEach((query) => query.reset())
    }

    useEffect(() => {
        const isLoading =
            queryList.findIndex((q) => q.isLoading) > -1 || mutationList.findIndex((q) => q.isLoading) > -1
        const isError = queryList.findIndex((q) => q.isError) > -1 || mutationList.findIndex((q) => q.isError) > -1
        const isSuccess =
            queryList.findIndex((q) => q.isSuccess || q.isIdle) > -1 ||
            mutationList.findIndex((q) => q.isSuccess || q.isIdle) > -1

        console.log('isLoading: ' + isLoading)
        console.log('isError: ' + isError)
        console.log('isSuccess: ' + isSuccess)

        let newResult: QueriesStatus = ''
        if (isLoading) newResult = 'isLoading'
        else if (isError) newResult = 'isError'
        else if (isSuccess) newResult = 'isSuccess'
        if (result != newResult) {
            console.log('new result -> ' + newResult)
            setResult(newResult)
        }
        const errorQuery = queryList.find((q) => q.error)
        const errorMutation = mutationList.find((q) => q.error)
        if (errorQuery) {
            errorQuery.error?.message
                ? setMessage(errorQuery.error.message)
                : setMessage(JSON.stringify(errorQuery.error))
        }
        if (errorMutation) {
            errorMutation.error.message
                ? setMessage(errorMutation.error.message)
                : setMessage(JSON.stringify(errorMutation.error))
        }
    }, [queryList.map((q) => q.status)])

    return {
        result: result,
        errorMessage: message,
        handleResetStatus: handleResetStatus,
    }
}
