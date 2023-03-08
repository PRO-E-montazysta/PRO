import { Paper } from "@mui/material";
import { Box } from "@mui/system";
import { AxiosError } from "axios";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { getToolTypeDetails } from "../../api/toolType.api";
import { ToolType } from "../../types/model/ToolType";



const ToolTypeDetails = () => {
    const params = useParams();

    const { isLoading, isError, error, data } = useQuery<ToolType, AxiosError>(
        ['tooltype', { id: params.id }],
        async () => getToolTypeDetails(params.id),
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

export default ToolTypeDetails