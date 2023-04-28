import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useToolEventFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddToolEvent, useDeleteToolEvent, useEditToolEvent, useToolEventData } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormTitle from '../../components/form/FormTitle'
import FormBox from '../../components/form/FormBox'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import { useQuery } from 'react-query'
import { Tool, ToolHistory } from '../../types/model/Tool'
import { AxiosError } from 'axios'
import { getAllTools, getToolHistory } from '../../api/tool.api'
import { Table, TableHead, TableRow, TableCell, TableBody, Card, Grid, Typography } from '@mui/material'
import { formatDate } from '../../helpers/format.helper'
import ExpandMore from '../../components/expandMore/ExpandMore'
import HistoryIcon from '@mui/icons-material/History'

const ToolEventDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const toolEventFormStructure = useToolEventFormStructure()
    const [initData, setInitData] = useState(getInitValues(toolEventFormStructure))

    //mutations and queries
    const addToolEventMutation = useAddToolEvent()
    const editToolEventMutation = useEditToolEvent((data) => handleOnEditSuccess(data))
    const deleteToolEventMutation = useDeleteToolEvent(() => toolEventData.remove())
    const toolEventData = useToolEventData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [toolEventData],
        [addToolEventMutation, editToolEventMutation, deleteToolEventMutation],
    )

    const handleSubmit = (values: any) => {
        if (pageMode === 'new') addToolEventMutation.mutate(values)
        else if (pageMode === 'edit') editToolEventMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć zgłoszenie?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result === 1 && params.id && Number.isInteger(params.id)) deleteToolEventMutation.mutate(params.id)
            },
        })
    }

    const formik = useFormik({
        initialValues: initData,
        validationSchema: getValidatinSchema(toolEventFormStructure, pageMode),
        onSubmit: handleSubmit,
    })

    const handleReset = () => {
        formik.resetForm()
        formik.setValues(initData)
    }

    const handleCancel = () => {
        handleReset()
        setPageMode('read')
    }

    const handleOnEditSuccess = (data: any) => {
        toolEventData.refetch({
            queryKey: ['ToolEvent', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (toolEventData.data) {
            formik.setValues(toolEventData.data)
            setInitData(toolEventData.data)
        }
    }, [toolEventData.data])

    useEffect(() => {
        if (params.id === 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(toolEventFormStructure))
            setInitData(getInitValues(toolEventFormStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    const queryTools = useQuery<Array<Tool>, AxiosError>(['tool-list'], getAllTools, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })

    const queryToolHistory = useQuery<Array<ToolHistory>, AxiosError>(
        ['tool-history'],
        async () => getToolHistory(params.id && params.id !== 'new' ? params.id : ''),
        {
            enabled: !!params.id && params.id !== 'new',
        },
    )

    const addToolHistoryTableCard = () => {
        return (
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Nazwa etapu</TableCell>
                        <TableCell align="right">Brygadzista</TableCell>
                        <TableCell align="right">Początek etapu</TableCell>
                        <TableCell align="right">Koniec etapu</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {queryToolHistory.data &&
                        queryToolHistory.data.map((row) => (
                            <TableRow
                                key={row['orderStageName']}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    {row['orderStageName']}
                                </TableCell>
                                <TableCell align="right">{row['foremanName']}</TableCell>
                                <TableCell align="right">{formatDate(row['orderStageStartDate'])}</TableCell>
                                <TableCell align="right">{formatDate(row['orderStageStartDate'])}</TableCell>
                            </TableRow>
                        ))}
                </TableBody>
            </Table>
        )
    }

    const addToolHistory = () => {
        if (queryToolHistory.data && queryToolHistory.data?.length > 0) {
            return (
                <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                    <Card sx={{ width: '100%', left: '50%' }}>
                        <ExpandMore
                            titleIcon={<HistoryIcon />}
                            title="Historia narzędzia"
                            cardContent={addToolHistoryTableCard()}
                        />
                    </Card>
                </Grid>
            )
        } else {
            return (
                <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                    <Typography>Narzędzie nie było jeszcze używane podczas etapów</Typography>
                </Grid>
            )
        }
    }

    return (
        <>
            <FormBox>
                {/* TODO check if this return nice result  */}
                <FormTitle
                    text={
                        pageMode === 'new'
                            ? 'Nowa usterka narzędzia'
                            : 'Usterka ' +
                              queryTools.data
                                  ?.filter((f) => f.id === formik.values['elementId'])
                                  .map((x) => x.name + ' - ' + x.code)
                    }
                />
                <FormPaper>
                    {queriesStatus.result !== 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
                            <FormStructure formStructure={toolEventFormStructure} formik={formik} pageMode={pageMode} />
                            {pageMode !== 'new' ? addToolHistory() : ''}
                            <FormButtons
                                id={params.id}
                                onCancel={handleCancel}
                                onDelete={handleDelete}
                                onEdit={() => setPageMode('edit')}
                                onReset={handleReset}
                                onSubmit={formik.submitForm}
                                readonlyMode={pageMode === 'read'}
                            />
                        </>
                    )}
                </FormPaper>
            </FormBox>
        </>
    )
}

export default ToolEventDetails
