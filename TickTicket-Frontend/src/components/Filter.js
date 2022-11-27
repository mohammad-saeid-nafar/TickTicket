import React from "react";
import { styled } from '@mui/material/styles';
import { Box, Slider, Paper, Stack, Button, Typography, TextField, Select, MenuItem} from "@mui/material";
// import DatePicker from "react-datepicker"

const Filter = (props) => {
  const [costRange, setCostRange] = React.useState([0, 500]);
  const [date, setDate] = React.useState();
  const [area, setArea] = React.useState();
  const [eventType, setEventType] = React.useState();

  
  const handleCostChange = (_, newValue) => {
    setCostRange(newValue);
  };

  const handleDateChange = (_, newValue) => {
    setDate(newValue);
  };
  
  const handleAreaChange = (_, newValue) => {
    setArea(newValue);
  };
  
  const handleDropDown = (_, newValue) => {
    setEventType(newValue);
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
        <Item>
        Date
        <TextField
            id="first-name"
            label="Date"
            margin="normal"
            value={date}
            onChange={handleDateChange}
            height="100"
            style={{width: 240}}
        />
          <Button variant="outlined" onClick={() => props.filterByDate(date)}>Find events</Button>
        </Item>
        <Item>
        Area
        <TextField
            id="first-name"
            label="Area"
            margin="normal"
            onChange={handleAreaChange}
            style={{width: 240}}
            />
          <Button variant="outlined" onClick={() => props.filterByArea(area)}>Find events</Button>
        </Item>
        <Item>
        Event Type
        <div><Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            style={{ width: 240}}
            label="Age"
            onChange={handleDropDown}
        >
        <MenuItem value={10}>Ten</MenuItem>
        <MenuItem value={20}>Twenty</MenuItem>
        <MenuItem value={30}>Thirty</MenuItem>
        </Select></div>
          <Button variant="outlined" onClick={() => props.filterByEventType(eventType)}>Find events</Button>
        </Item>
        <Button variant="outlined" onClick={clearFilters}>Clear filters</Button>
      </Stack>
    </Box>
  );
};

export default Filter;
