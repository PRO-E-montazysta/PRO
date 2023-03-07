import { useQuery } from 'react-query';
import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Employee } from '../../../types/model/Employee';
import { getUser } from '../../../api/user.api';
// import { Avatar, Card, CardContent, CardHeader, CardMedia, IconButton, Typography } from '@mui/material';
// import FavoriteIcon from '@mui/icons-material/Favorite';
// import ShareIcon from '@mui/icons-material/Share';
// import MoreVertIcon from '@mui/icons-material/MoreVert';
// import { styled } from '@mui/material/styles';

import * as React from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import WorkHistoryIcon from '@mui/icons-material/WorkHistory';
import { Grid } from '@mui/material';
import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar';

interface ExpandMoreProps extends IconButtonProps {
  expand: boolean;
}

const ExpandMore = styled((props: ExpandMoreProps) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));

const EmpDetails = () => {
  const params = useParams();
  const [expandedHistory, setExpandedHistory] = useState(false);
  const [expandedInformation, setExpandedInformation] = useState(false);
  const [expandedStatus, setExpandedStatus] = useState(false);

  const queryData = useQuery<Employee, AxiosError>(['users', { id: params.id }], async () => getUser(params.id!), {
    enabled: !!params.id,
  });
  console.log(queryData);

  const handleExpandHistoryClick = () => {
    setExpandedHistory(!expandedHistory);
  };

  const handleExpandInformationClick = () => {
    setExpandedInformation(!expandedInformation);
  };

  const handleExpandStatusClick = () => {
    setExpandedStatus(!expandedStatus);
  };

  const getUserRoles = () => {
    return queryData.data?.roles.map((role) => role);
  };

  const getStatusDescription = ()=>{
    if(queryData.data?.status === 'AVAIBLE'){
        return "Pracownik jest dostępny"
    }else{
        return queryData.data?.unavailbilityDescription
    }
    }

  return (
    <Grid container alignItems="center" justifyContent="center" marginTop={2}>
      <Card sx={{ minWidth: 500, left: '50%' }}>
        <CardHeader
          avatar={
            <Avatar sx={{ bgcolor: 'red' }} aria-label="recipe">
              R
            </Avatar>
          }
          action={
            <IconButton aria-label="settings">
              <MoreVertIcon />
            </IconButton>
          }
          title={`${queryData.data?.firstName} ${queryData.data?.lastName}`}
          subheader={getUserRoles()}
        />
        <CardActions disableSpacing>
          <IconButton aria-label="share">
            <PermContactCalendarIcon />
          </IconButton>
          <Typography variant="body2" color="text.secondary">
            Informacje kontaktowe
          </Typography>
          <ExpandMore
            expand={expandedInformation}
            onClick={handleExpandInformationClick}
            aria-expanded={expandedInformation}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </ExpandMore>
        </CardActions>
        <Collapse in={expandedInformation} timeout="auto" unmountOnExit>
          <CardContent>
            <Typography variant="body2" color="text.secondary">
              Numer telefonu: {queryData.data?.phone}, E-mail: {queryData.data?.email}
            </Typography>
          </CardContent>
        </Collapse>

        <CardActions disableSpacing>
          <IconButton aria-label="share">
            <WorkHistoryIcon />
          </IconButton>
          <Typography variant="body2" color="text.secondary">
            Status: {queryData.data?.status}
          </Typography>
          <ExpandMore
            expand={expandedStatus}
            onClick={handleExpandStatusClick}
            aria-expanded={expandedStatus}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </ExpandMore>
        </CardActions>
        <Collapse in={expandedStatus} timeout="auto" unmountOnExit>
          <CardContent>
            <Typography variant="body2" color="text.secondary">
                {getStatusDescription()}
            </Typography>
          </CardContent>
        </Collapse>

        <CardActions disableSpacing>
          <IconButton aria-label="share">
            <WorkHistoryIcon />
          </IconButton>
          <Typography variant="body2" color="text.secondary">
            Historia pracy w tabeli
          </Typography>
          <ExpandMore
            expand={expandedHistory}
            onClick={handleExpandHistoryClick}
            aria-expanded={expandedHistory}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </ExpandMore>
        </CardActions>
        <Collapse in={expandedHistory} timeout="auto" unmountOnExit>
          <CardContent>
            <Typography variant="body2" color="text.secondary">
              Możemy tu wyświetlać historię pracy. Zlecenie, etap, data.
            </Typography>
          </CardContent>
        </Collapse>
      </Card>
    </Grid>
  );
};

export default EmpDetails;
