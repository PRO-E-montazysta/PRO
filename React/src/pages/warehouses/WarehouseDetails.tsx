import { Paper } from "@mui/material";
import { Box } from "@mui/system";
import { AxiosError } from "axios";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { getWarehouseDetails } from "../../api/warehouse.api";
import { Warehouse } from "../../types/model/Warehouse";



const WarehouseDetails = () => {
    const params = useParams();

    const { isLoading, isError, error, data } = useQuery<Warehouse, AxiosError>(
        ['warehouse', { id: params.id }],
        async () => getWarehouseDetails(params.id),
        {
            enabled: !!params.id
        }
    )




    return <Box>
        <Paper>
            {isLoading ? <p>Ładowanie...</p>
                : isError ? <p>{error.message}</p>
                    : JSON.stringify(data)
            }
        </Paper>
    </Box>
}

export default WarehouseDetails