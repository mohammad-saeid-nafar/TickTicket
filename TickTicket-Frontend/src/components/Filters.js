import React from "react";
import {
  Box,
  Slider,
  Stack,
  Button,
  Typography,
  TextField,
  Select,
  MenuItem,
} from "@mui/material";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import Filter from "./Filter";
import axios from "axios";

const Filters = (props) => {
  const [costRange, setCostRange] = React.useState([0, 500]);
  const [date, setDate] = React.useState();
  const [area, setArea] = React.useState("");
  const [eventType, setEventType] = React.useState();

  const handleCostChange = (_, newValue) => {
    setCostRange(newValue);
  };

  const handleAreaChange = (event) => {
    setArea(event.target.value);
  };

  const handleDropdown = (event) => {
    console.log(event.target.value);
    setEventType(event.target.value);
  };

  const clearFilters = () => {
    setCostRange([0, 500]);
    setDate(Date.now());
    setArea("");
    eventTypes.length !== 0 && setEventType(eventTypes[0].name);
    props.clearFilter();
  };
  const [eventTypes, setEventTypes] = React.useState([]);

  React.useEffect(() => {
    axios.get("event-types").then((res) => {
      setEventTypes(res.data);
      eventTypes.length !== 0 && setEventType(eventTypes[0].name);
    });
    // eslint-disable-next-line
  }, []);

  return (
    <Box width="100%">
      <Stack direction="row" spacing={2} width="100%">
        <Filter>
        <Typography>
            Cost
          </Typography>
          <Typography marginTop={2}>
            ${costRange[0]} - ${costRange[1]}
          </Typography>
          <Slider
            sx={{ width: "90%"}}
            min={0}
            max={500}
            value={costRange}
            onChange={handleCostChange}
            valueLabelDisplay="auto"
          />
          <Button
            variant="outlined"
            onClick={() => props.filterByCost(costRange)}
          >
            Find events
          </Button>
        </Filter>
        <Filter>
          <Typography paddingBottom={2}>Date</Typography>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              value={date}
              onChange={(newValue) => {
                setDate(newValue);
              }}
              renderInput={(params) => <TextField {...params} />}
            />
          </LocalizationProvider>
          <Button sx={{ marginTop: 1}} variant="outlined" onClick={() => props.filterByDate(date)}>
            Find events
          </Button>
        </Filter>
        <Filter>
          <Typography>Area</Typography>
          <TextField
            id="area-filter"
            label="Area"
            margin="normal"
            defaultValue={""}
            value={area}
            onChange={handleAreaChange}
            style={{ width: 240 }}
          />
          <Button variant="outlined" onClick={() => props.filterByArea(area)}>
            Find events
          </Button>
        </Filter>
        <Filter>
          <Typography>Event Type</Typography>
          <Select
            id="event type select"
            style={{ width: 240, marginTop: 15, marginBottom: 5 }}
            defaultValue={eventTypes.length !== 0 ? eventTypes[0].name: ""}
            value={eventType}
            onChange={handleDropdown}
          >
            {eventTypes.map((eventType) => {
              return (
                <MenuItem key={eventType.id} value={eventType.name}>
                  {eventType.name}
                </MenuItem>
              );
            })}
          </Select>
          <Button
            variant="outlined"
            onClick={() => props.filterByEventType(eventType)}
          >
            Find events
          </Button>
        </Filter>
        <Button variant="outlined" onClick={clearFilters}>
          Clear filters
        </Button>
      </Stack>
    </Box>
  );
};

export default Filters;
