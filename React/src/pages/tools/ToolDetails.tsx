import { Paper } from "@mui/material";
import { Box } from "@mui/system";
import { AxiosError } from "axios";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { getToolDetails } from "../../api/tool.api";
import { Tool } from "../../types/model/Tool";



const ToolDetails = () => {
    const params = useParams();

    const { isLoading, isError, error, data } = useQuery<Tool, AxiosError>(
        ['tool', { id: params.id }],
        async () => getToolDetails(params.id),
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

export default ToolDetails