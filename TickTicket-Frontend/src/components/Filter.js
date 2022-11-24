import React from "react";
import { styled } from '@mui/material/styles';
import { Box, Slider, Paper, Stack, Button, Typography} from "@mui/material";

const Filter = (props) => {
  const [costRange, setCostRange] = React.useState([0, 500]);
  
  const handleCostChange = (_, newValue) => {
    setCostRange(newValue);
  };
  
  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    textAlign: 'center',
    color: theme.palette.text.secondary,
    width: '100%',
    }));
  
  const clearFilters = () => {
    setCostRange([0, 500]);
    props.clearFilter();
  }

  return (
    <Box width="100%">
      <Stack direction="row" spacing={2} width="100%">
        <Item>
          <Typography>
            ${costRange[0]} - ${costRange[1]}
          </Typography>
          <Slider
            sx={{ width: "90%" }}
            min={0}
            max={500}
            value={costRange}
            onChange={handleCostChange}
            valueLabelDisplay="auto"
          />
          <Button variant="outlined" onClick={() => props.filterByCost(costRange)}>Find events</Button>
        </Item>
        <Item>Filter 2</Item>
        <Item>Filter 3</Item>
        <Button variant="outlined" onClick={clearFilters}>Clear filters</Button>
      </Stack>
    </Box>
  );
};

export default Filter;
