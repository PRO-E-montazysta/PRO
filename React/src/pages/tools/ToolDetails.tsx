import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddTool, useToolData, useDeleteTool, useEditTool } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import { getToolHistory } from '../../api/tool.api'
import { ToolHistory } from '../../types/model/Tool'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { Card, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from '@mui/material'
import { formatDate } from '../../helpers/format.helper'
import ExpandMore from '../../components/expandMore/ExpandMore'
import HistoryIcon from '@mui/icons-material/History'

const ToolDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addToolMutation = useAddTool()
    const editToolMutation = useEditTool((data) => handleOnEditSuccess(data))
    const deleteToolMutation = useDeleteTool(() => toolData.remove())
    const toolData = useToolData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus([toolData], [addToolMutation, editToolMutation, deleteToolMutation])

    const handleSubmit = (values: any) => {
        if (pageMode === 'new') addToolMutation.mutate(values)
        else if (pageMode === 'edit') editToolMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć narzędzie?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result === 1 && params.id && Number.isInteger(params.id)) deleteToolMutation.mutate(params.id)
            },
        })
    }

    const formik = useFormik({
        initialValues: initData,
        validationSchema: getValidatinSchema(formStructure, pageMode),
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
        toolData.refetch({
            queryKey: ['tool', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (toolData.data) {
            formik.setValues(toolData.data)
            setInitData(toolData.data)
        }
    }, [toolData.data])

    useEffect(() => {
        if (params.id === 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            setInitData(getInitValues(formStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

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
        <FormBox>
            <FormTitle text={pageMode === 'new' ? 'Nowe narzędzie' : formik.values['name']} />
            <FormPaper>
                {queriesStatus.result !== 'isSuccess' ? (
                    <QueryBoxStatus queriesStatus={queriesStatus} />
                ) : (
                    <>
                        <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
                        {pageMode !== 'new' ? addToolHistory() : ''}
                        <FormButtons
                            id={params.id}
                            onCancel={handleCancel}
                            onDelete={handleDelete}
                            onEdit={() => setPageMode('edit')}
                            onReset={handleReset}
                            onSubmit={formik.submitForm}
                            readonlyMode={pageMode === 'read'}
                            printLabel={[toolData.data?.name as string, toolData.data?.code as string]}
                        />
                    </>
                )}
            </FormPaper>
        </FormBox>
    )
}

export default ToolDetails
