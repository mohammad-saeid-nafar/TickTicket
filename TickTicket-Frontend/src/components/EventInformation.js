import {
  Button,
  ButtonGroup,
  Container,
  Stack,
  TextField,
} from "@mui/material";
import { Box } from "@mui/system";
import CancelIcon from "@mui/icons-material/Cancel";
import CheckIcon from "@mui/icons-material/Check";
import * as React from "react";
import { useEffect, useState } from "react";
import axios from "axios";

const EventInformation = (props) => {
  const [eventTypes, setEventTypes] = React.useState([]);
  const [chosenEventTypes, setChosenEventTypes] = React.useState(
    props.event ? props.event.eventTypes.map((type) => type.id) : [],
  );
  const [buttonVariants, setButtonVariants] = React.useState(new Map());
  const [eventName, setEventName] = useState(
    props.event ? props.event.name : "",
  );
  const [eventDescription, setEventDescription] = useState(
    props.event ? props.event.description : "",
  );
  const [eventCapacity, setEventCapacity] = useState(
    props.event ? props.event.capacity : "",
  );
  const [eventCost, setEventCost] = useState(
    props.event ? props.event.cost : "",
  );
  const [eventStart, setEventStart] = useState(
    props.event ? props.event.start : "",
  );
  const [eventEnd, setEventEnd] = useState(props.event ? props.event.end : "");
  const [eventAddress, setEventAddress] = useState(
    props.event ? props.event.address : "",
  );
  const [eventPhoneNumber, setEventPhoneNumber] = useState(
    props.event ? props.event.phoneNumber : "",
  );
  const [eventEmail, setEventEmail] = useState(
    props.event ? props.event.email : "",
  );
  const [disable, setDisable] = React.useState(true);

  useEffect(() => {
    axios.get("event-types").then((res) => {
      setEventTypes(res.data);
    });

    if (
      eventName !== "" &&
      eventDescription !== "" &&
      eventCapacity !== "" &&
      eventCost !== "" &&
      eventStart !== "" &&
      eventEnd !== "" &&
      eventAddress !== "" &&
      eventPhoneNumber !== "" &&
      eventEmail !== ""
    ) {
      setDisable(false);
    } else {
      setDisable(true);
    }
  }, [
    setDisable,
    eventName,
    eventDescription,
    eventCapacity,
    eventCost,
    eventStart,
    eventEnd,
    eventAddress,
    eventPhoneNumber,
    eventEmail,
  ]);

  const handleOnEventTypeClick = (eventType) => {
    if (chosenEventTypes.find((id) => id === eventType.id) === undefined) {
      chosenEventTypes.push(eventType.id);
    } else {
      chosenEventTypes.splice(chosenEventTypes.indexOf(eventType.id), 1);
    }
    setChosenEventTypes(chosenEventTypes);
    getButtonVariants();
  };

  const getButtonVariants = () => {
    let tempMap = buttonVariants;
    tempMap = new Map();
    eventTypes.forEach((eventType) => {
      tempMap.set(
        eventType.id,
        chosenEventTypes.includes(eventType.id) ? "contained" : "outlined",
      );
    });
    setButtonVariants(tempMap);
  };

  const handleCancelCreate = () => {
    console.log(props.eventName);
    setEventName("");
    setEventDescription("");
    setEventCapacity("");
    setEventCost("");
    setEventStart("");
    setEventEnd("");
    setEventAddress("");
    setEventPhoneNumber("");
    setEventEmail("");
  };

  return (
    <Container
      aria-labelledby="event-information-title"
      aria-describedby="event-information-description"
      sx={{
        paddingTop: "3%",
        minHeight: "500",
        spacing: "3",
        border: 1,
        p: 1,
        bgcolor: "background.paper",
        position: props.event ? "absolute" : "static",
        top: props.event ? "50%" : "auto",
        left: props.event ? "50%" : "auto",
        transform: props.event ? "translate(-50%, -50%)" : "None",
      }}
    >
      <Stack spacing={2}>
        <Box textAlign="left">
          Select Event Type(s):
          <ButtonGroup>
            {eventTypes.map((eventType) => {
              return (
                <Button
                  key={eventType.id}
                  onClick={() => handleOnEventTypeClick(eventType)}
                  variant={chosenEventTypes.includes(eventType.id) ? "contained" : "outlined"}
                >
                  {eventType.name}{" "}
                </Button>
              );
            })}
          </ButtonGroup>
          <TextField
            fullWidth
            required
            id="outlined-required"
            label="Event Name"
            helperText="Event Name"
            value={eventName}
            onChange={(event) => setEventName(event.target.value)}
          />
          <TextField
            required
            fullWidth
            id="outlined-required"
            label="Description"
            helperText="Description"
            value={eventDescription}
            onChange={(event) => setEventDescription(event.target.value)}
          />
        </Box>
        <Box textAlign="center">
          <TextField
            fullWidth
            required
            id="outlined-required"
            label="Capacity"
            helperText="Capacity"
            value={eventCapacity}
            onChange={(event) => setEventCapacity(event.target.value)}
          />
          <TextField
            fullWidth
            required
            id="outlined-required"
            label="Cost"
            helperText="Cost"
            value={eventCost}
            onChange={(event) => setEventCost(event.target.value)}
          />
        </Box>
        <Box textAlign="center">
          <TextField
            fullWidth
            required
            id="outlined-required"
            label="Address"
            helperText="Address"
            value={eventAddress}
            onChange={(event) => setEventAddress(event.target.value)}
          />
          <TextField
            required
            fullWidth
            id="outlined-required"
            label="Phone Number"
            helperText="Phone Number"
            value={eventPhoneNumber}
            onChange={(event) => setEventPhoneNumber(event.target.value)}
          />
        </Box>
        <Box textAlign="center">
          <TextField
            required
            fullWidth
            id="outlined-required"
            label="Email"
            helperText="Email"
            value={eventEmail}
            onChange={(event) => setEventEmail(event.target.value)}
          />
        </Box>
        <Box textAlign="center">
          <TextField
            fullWidth
            id="datetime-local"
            label="Start Date and Time"
            type="datetime-local"
            defaultValue="2022-05-24T10:30:00"
            sx={{ width: 250 }}
            InputLabelProps={{
              shrink: true,
            }}
            value={eventStart}
            onChange={(event) => setEventStart(event.target.value)}
          />

          <TextField
            fullWidth
            id="datetime-local"
            label="Start Date and Time"
            type="datetime-local"
            defaultValue="2022-05-24T10:30:00"
            sx={{ width: 250 }}
            InputLabelProps={{
              shrink: true,
            }}
            value={eventEnd}
            onChange={(event) => setEventEnd(event.target.value)}
          />
        </Box>
        <Box textAlign="center">
          <Button
            variant="contained"
            onClick={handleCancelCreate}
            color="error"
          >
            <CancelIcon />
            Cancel
          </Button>
          <Button
            variant="contained"
            onClick={() =>
              props.handleAction(
                eventName,
                eventDescription,
                eventCapacity,
                eventCost,
                eventStart,
                eventEnd,
                eventAddress,
                eventPhoneNumber,
                eventEmail,
                chosenEventTypes,
              )
            }
            color="success"
            disabled={disable}
          >
            <CheckIcon />
            {props.event ? "Edit Event" : "Create Event"}
          </Button>
        </Box>
      </Stack>
    </Container>
  );
};

export default EventInformation;
