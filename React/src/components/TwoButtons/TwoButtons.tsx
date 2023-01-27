import { Button, Grid } from '@mui/material';
import React from 'react';

type TwoButtonTypes = {
  firstBtnValue: string;
  secondBtnValue: string;
  firstBtnFunction?: () => {};
  secondBtnFunction?: () => {};
};

const TwoButtons = ({ firstBtnValue, secondBtnValue }: TwoButtonTypes) => {
  return (
    <Grid spacing={2} container justifyContent="flex-end">
      <Grid item>
        <Button color="primary" variant="contained">
          {firstBtnValue}
        </Button>
      </Grid>
      <Grid item>
        <Button color="secondary" variant="text">
          {secondBtnValue}
        </Button>
      </Grid>
    </Grid>
  );
};

export default TwoButtons;
