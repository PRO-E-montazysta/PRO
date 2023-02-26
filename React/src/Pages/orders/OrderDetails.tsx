import { Paper } from "@mui/material";
import { Box } from "@mui/system";
import { AxiosError } from "axios";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { getOrderDetails } from "../../api/order.api";
import { Order } from "../../types/model/Order";



const OrderDetails = () => {
    const params = useParams();

    const { isLoading, isError, error, data } = useQuery<Order, AxiosError>(
        ['order', { id: params.id }],
        async () => getOrderDetails(params.id),
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

export default OrderDetails