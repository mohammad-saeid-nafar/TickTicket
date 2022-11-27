import React from "react";
import { styled } from '@mui/material/styles';
import { Box, Slider, Paper, Stack, Button, Typography, TextField, Select, MenuItem, DatePicker} from "@mui/material";
// import DatePicker from "react-datepicker"
import axios from "axios";

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

  const handleDropDown = (event) => {
    setEventType(event.target.value);
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
  const [eventTypes, setEventTypes] = React.useState([]);

  React.useEffect(() => {
    axios.get("event-types").then((res) => {
        setEventTypes(res.data);
    });
}, []);

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
        {/* <TextField
            id="first-name"
            label="Date"
            margin="normal"
            value={date}
            onChange={handleDateChange}
            height="100"
            style={{width: 240}}
        /> */}
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DatePicker
            label="Basic example"
            value={date}
            onChange={handleDateChange}
            renderInput={(params) => <TextField {...params} />}
          />
          </LocalizationProvider>
          <Button variant="outlined" onClick={() => props.filterByDate(date)}>Find events</Button>
        </Item>
        <Item>
        Area
        <TextField
            id="first-namee"
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
            value={eventType}
            onChange={handleDropDown}
        >
          {eventTypes.map((eventType) => {
            return (
              <MenuItem value={eventType.id}>{eventType.name}</MenuItem>
            )
          })}
        </Select></div>
          <Button variant="outlined" onClick={() => props.filterByEventType(eventType)}>Find events</Button>
        </Item>
        <Button variant="outlined" onClick={clearFilters}>Clear filters</Button>
      </Stack>
    </Box>
  );
};

export default Filter;