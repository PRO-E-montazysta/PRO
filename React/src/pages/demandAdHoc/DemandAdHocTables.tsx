import React from 'react'
import { useFormik } from 'formik'
import { useContext, useEffect, useRef, useState } from 'react'

import { Box, Divider, Tabs, Tab, Typography } from '@mui/material'
import { TabPanel } from '../orderStages/helper'
import { v4 as uuidv4 } from 'uuid'
import OrderStageToolsTable from '../orderStages/OrderStageToolsTable'
import OrderStageElementsTable from '../orderStages/OrderStageElementsTable'
import { useQuery } from 'react-query'
import { ToolType } from '../../types/model/ToolType'
import { AxiosError } from 'axios'
import { getAllToolTypes } from '../../api/toolType.api'
import { OrderStage } from '../../types/model/OrderStage'
import { getAllElements } from '../../api/element.api'

type DemandAdHocTablesTypes = {
    plannedElementsRef: React.MutableRefObject<
        {
            numberOfElements: number
            elementId: string
        }[]
    >
    plannedToolsTypesRef: React.MutableRefObject<
        {
            numberOfTools: number
            toolTypeId: string
        }[]
    >
    pageMode: string
}

const DemandAdHocTables = React.memo(({ plannedElementsRef, plannedToolsTypesRef,pageMode}: DemandAdHocTablesTypes) => {
    const [tabValue, setTabValue] = useState(0)

    const tabProps = (index: number) => {
        return {
            id: `simple-tab-${index}`,
            'aria-controls': `simple-tabpanel-${index}`,
        }
    }

    const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
        setTabValue(newValue)
    }

    const handleSetPlannedElements = (value: { numberOfElements: number; elementId: string }[]) => {
        plannedElementsRef!.current! = value
    }
    const handleSetPlannedToolsTypes = (value: { numberOfTools: number; toolTypeId: string }[]) => {
        plannedToolsTypesRef!.current! = value
    }

    const queryAllToolTypes = useQuery<Array<ToolType>, AxiosError>(
        ['toolType-list'],
        async () => await getAllToolTypes(),
    )

    const queryAllElements = useQuery<Array<OrderStage>, AxiosError>(
        ['elements-list'],
        async () => await getAllElements(),
    )


    return (
        <Box sx={{ marginTop: '20px', width: '100%' }}>
            <Box
                sx={{
                    borderBottom: 1,
                    borderColor: 'divider',
                    display: 'flex',
                    justifyContent: 'space-around',
                }}
            >
                <Tabs component="div" value={tabValue} onChange={handleTabChange} aria-label="basic tabs example">
                    <Tab label="Lista typów narzędzi" {...tabProps(0)} />
                    <Tab label="Lista elementów" {...tabProps(1)} />
                </Tabs>
            </Box>
            <TabPanel key={uuidv4()} value={tabValue} index={0}>
                <OrderStageToolsTable
                    itemsArray={queryAllToolTypes.data!}
                    isDisplayingMode={pageMode === 'read'? true: false}
                    toolsTypeListIds={[]}
                    handleChange={handleSetPlannedToolsTypes}
                    toolsRef={plannedToolsTypesRef}
                />
            </TabPanel>
            <TabPanel key={uuidv4()} value={tabValue} index={1}>
                <OrderStageElementsTable
                    itemsArray={queryAllElements.data!}
                    isDisplayingMode={pageMode === 'read'? true: false}
                    elementsListIds={[]}
                    handleChange={handleSetPlannedElements}
                    elementsRef={plannedElementsRef}
                />
            </TabPanel>
        </Box>
    )
})

export default DemandAdHocTables
