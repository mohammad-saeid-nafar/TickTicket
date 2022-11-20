// import * as React from 'react';
// import axios from "axios";
// import ToggleButton from '@mui/material/ToggleButton';
// import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
//
// const CreateEvent = () => {
//   const [eventTypes, setEventTypes] = React.useState([]);
//   const [alignment, setAlignment] = React.useState('web');
//   const [eventType, setEventType] = React.useState("");
//
//   React.useEffect(() => {
//     axios.get("event-types").then((res) => {
//         setEventTypes(res.data);
//     });
//   }, []);
//
//   const handleChange = (
//     event,
//     newAlignment,
//   ) => {
//     setAlignment(newAlignment);
//     setEventType(event.target.value);
//     // Todo remove console log line when eventType gets used
//     console.log(eventType);
//   };
//
//   return(
//     <>
//       <h1>Create Event</h1>
//       <h2>1. Select Event Type</h2>
//       <ToggleButtonGroup
//         color="primary"
//         value={alignment}
//         exclusive
//         onChange={handleChange}
//       >
//         {eventTypes.map((eventType) => {
//           return (
//             <ToggleButton value={eventType.name}>{eventType.name}</ToggleButton>
//           );
//         })}
//       </ToggleButtonGroup>
//     </>
//   );
// };
// export default CreateEvent;

import {Alert, Button, Chip, Container, Stack, TextField} from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import EventCard from "../components/EventCard";
import * as React from "react";
import AddIcon from "@mui/icons-material/Add";
import {Box} from "@mui/system";
import CancelIcon from "@mui/icons-material/Cancel";
import CheckIcon from "@mui/icons-material/Check";
import Popper from '@mui/material/Popper';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';

//TODO: Make it work with the event types
//TODO: Set the template date to today

const CreateEvent = () => {
    const [events, setEvents] = useState([]);
    const [eventTypes, setEventTypes] = React.useState([]);
    const [chosenEventTypes, setChosenEventTypes] = React.useState([]);
    const [eventName, setEventName] = useState("");
    const [eventDescription, setEventDescription] = useState("");
    const [eventCapacity, setEventCapacity] = useState("");
    const [eventCost, setEventCost] = useState("");
    const [eventStart, setEventStart] = useState("");
    const [eventEnd, setEventEnd] = useState("");
    const [eventAddress, setEventAddress] = useState("");
    const [eventPhoneNumber, setEventPhoneNumber] = useState("");
    const [eventEmail, setEventEmail] = useState("");

    const [createForm, setCreateForm] = React.useState(false);
    const [disable, setDisable] = React.useState(true);
    const [refresh, setRefresh] = React.useState(false);
    const [error, setError] = React.useState(false);
    const [errorMessage, setErrorMessage] = React.useState("");
    const [success, setSuccess] = React.useState(false);

    useEffect(() => {
        axios.get("events/organizer/Rico").then((res) => {
            setEvents(res.data);
        });
    }, [refresh]);

    useEffect(() => {
        axios.get("event-types").then((res) => {
            setEventTypes(res.data);
        });
    }, [refresh]);

    useEffect(() => {
        setCreateForm(false);
        setDisable(true);
    }, [refresh])

    useEffect(() => {
        if(eventName && eventDescription && eventCapacity && eventCost && eventStart && eventEnd && eventAddress && eventPhoneNumber && eventEmail){
            setDisable(false);
        }
        else{
            setDisable(true);
        }
    }, [setDisable, eventName, eventDescription, eventCapacity, eventCost, eventStart, eventEnd, eventAddress, eventPhoneNumber, eventEmail]);

    const [anchorEl, setAnchorEl] = React.useState(null);

    // const handleClick = (event) => {
    //     setAnchorEl(anchorEl ? null : event.currentTarget);
    // };

    const open = Boolean(anchorEl);
    const popperId = open ? 'simple-popper' : undefined;

    const handleClickCreateForm = (e) => {
        setSuccess(false);
        setCreateForm(true);
        setAnchorEl(anchorEl ? null : e.currentTarget);
    };

    const handleCancelCreateForm = () => {
        setCreateForm(false);
    };

    const handleEventNameChange = (event) => {
        setError(false);
        setEventName(event.target.value);
    };

    const handleEventDescriptionChange = (event) => {
        setError(false);
        setEventDescription(event.target.value);
    }

    const handleEventCapacityChange = (event) => {
        setError(false);
        setEventCapacity(event.target.value);
    }

    const handleEventCostChange = (event) => {
        setError(false);
        setEventCost(event.target.value);
    }

    const handleEventStartChange = (event) => {
        setError(false);
        setEventStart(event.target.value);
    }

    const handleEventEndChange = (event) => {
        setError(false);
        setEventEnd(event.target.value);
    }

    const handleEventAddressChange = (event) => {
        setError(false);
        setEventAddress(event.target.value);
    }

    const handleEventPhoneNumberChange = (event) => {
        setError(false);
        setEventPhoneNumber(event.target.value);
    }

    const handleEventEmailChange = (event) => {
        setError(false);
        setEventEmail(event.target.value);
    }

    const handleOnEventTypeclick = (eventType) => {
        console.log(chosenEventTypes.find(id => id === eventType.id))
        if(chosenEventTypes.find(id => id === eventType.id) === undefined){
            chosenEventTypes.push(eventType.id)
            setChosenEventTypes(chosenEventTypes);
        }
        else{
            chosenEventTypes.splice(1, chosenEventTypes.indexOf(eventType.id))
            setChosenEventTypes(chosenEventTypes);
        }
        console.log(chosenEventTypes)
    }

    const handleCreateEvent = React.useCallback(() => {
        if(!isNaN(eventName)){
            setError(true);
            setErrorMessage("Event Type Name Must Be A String");
        }
        else if(!isNaN(eventDescription)){
            setError(true);
            setErrorMessage("Event Type Description Must Be A String");
        }
        else if(isNaN(eventCapacity)){
            setError(true);
            setErrorMessage("Event Capacity Must Be A Number");
        }
        else if(isNaN(eventCost)){
            setError(true);
            setErrorMessage("Event Cost Must Be A Number");
        } else if(!isNaN(eventAddress)){
            setError(true);
            setErrorMessage("Event Address Must Be A String");
        } else if(isNaN(eventPhoneNumber)){
            setError(true);
            setErrorMessage("Event Phone Number Must Be A String");
        }
        else if(!isNaN(eventStart)){
            setError(true);
            setErrorMessage("Event Date Must Be A Number");
        }
        else if(!isNaN(eventEnd)){
            setError(true);
            setErrorMessage("Event Time Must Be A Number");
        }
        else{
            axios.post("events", {
                name: eventName,
                description: eventDescription,
                capacity: parseInt(eventCapacity),
                cost: parseFloat(eventCost),
                address: eventAddress,
                email: eventEmail,
                phoneNumber: eventPhoneNumber,
                organizerId: "827fa1fc-1907-41f3-bc02-3ceb0347aa0c",
                start: eventStart,
                end: eventEnd,
                eventTypes: []
            })
                .then(function (response) {
                    setSuccess(true);
                    setRefresh(true);
                })
                .catch(function (error) {
                    setError(true);
                    setErrorMessage(error);
                });
        }
    }, [eventName, eventDescription, eventCapacity, eventCost, eventStart, eventEnd, eventAddress, eventPhoneNumber, eventEmail]);


    return (
        <>
            <h1>Events</h1>
            <Container
                sx={{
                    paddingTop: "3%",
                }}
            >
                <Stack spacing={2}>
                    {events.map((event) => {
                        return <EventCard key={event.id} event={event} />;
                    })}
                </Stack>
            </Container>
            <Box textAlign='center' paddingTop="1%" paddingBottom="1%">
                <Button variant='contained' onClick={handleClickCreateForm}>
                    <AddIcon />
                    Add New Event
                </Button>
            </Box>
            { createForm &&
                <>
                    <Popper
                        id={popperId}
                        open={open}
                        anchorEl={anchorEl}
                        placement="top"
                        disablePortal={false}
                        modifiers={[
                            {
                                name: 'preventOverflow',
                                enabled: true,
                                options: {
                                    altAxis: true,
                                    altBoundary: true,
                                    tether: false,
                                    rootBoundary: 'document',
                                    padding: 8,
                                },
                            },
                        ]}
                    >
                        <Container
                            sx={{
                                paddingTop: "3%",
                                minHeight: "500",
                                spacing: "3",
                                border: 1, p: 1, bgcolor: 'background.paper'
                            }}
                        >
                            <Stack spacing={2}>
                                <Box textAlign='left'>
                                    Select Event Type(s):  
                                    {eventTypes.map((eventType) => {
                                        return (
                                            // <FormControlLabel control={<Checkbox />} label={eventType.name} onClick={() => handleOnEventTypeclick(eventType)}/>
                                            <Chip label={eventType.name} onClick={() => handleOnEventTypeclick(eventType)}/>
                                        );
                                    })}
                                    <TextField
                                        fullWidth
                                        required
                                        id="outlined-required"
                                        label="Event Name"
                                        helperText="Event Name"
                                        onChange={handleEventNameChange}
                                    />
                                    <TextField
                                        required
                                        fullWidth
                                        id="outlined-required"
                                        label="Description"
                                        // multiline
                                        // maxRows={4}
                                        helperText="Description"
                                        onChange={handleEventDescriptionChange}
                                    />
                                </Box>
                                <Box textAlign='center' >
                                    <TextField
                                        fullWidth
                                        required
                                        id="outlined-required"
                                        label="Capacity"
                                        helperText="Capacity"
                                        onChange={handleEventCapacityChange}
                                    />
                                    <TextField
                                        fullWidth
                                        required
                                        id="outlined-required"
                                        label="Cost"
                                        helperText="Cost"
                                        onChange={handleEventCostChange}
                                    />
                                </Box>
                                <Box textAlign='center' >
                                    <TextField
                                        fullWidth
                                        required
                                        id="outlined-required"
                                        label="Address"
                                        helperText="Address"
                                        onChange={handleEventAddressChange}
                                    />
                                    <TextField
                                        required
                                        fullWidth
                                        id="outlined-required"
                                        label="Phone Number"
                                        helperText="Phone Number"
                                        onChange={handleEventPhoneNumberChange}
                                    />
                                </Box>
                                <Box textAlign='center' >
                                    <TextField
                                        required
                                        fullWidth
                                        id="outlined-required"
                                        label="Email"
                                        helperText="Email"
                                        onChange={handleEventEmailChange}
                                    />
                                    <FormControl fullWidth>
                                        <InputLabel id="demo-simple-select-helper-label">Event Types</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-helper-label"
                                            id="demo-simple-select-helper"
                                            label="Event Types"
                                            displayEmpty
                                            helperText="Event Types"
                                        >
                                            <MenuItem value="">
                                                <em>None</em>
                                            </MenuItem>
                                            { eventTypes.map((eventType) => {
                                                return <MenuItem value={eventType.id}>{eventType.name}</MenuItem>

                                            })}
                                        </Select>
                                    </FormControl>
                                </Box>
                                <Box textAlign='center' >
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
                                        onChange={handleEventStartChange}
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
                                        onChange={handleEventEndChange}
                                    />


                                </Box>
                                <Box textAlign='center'>
                                    <Button variant='contained' onClick={handleCancelCreateForm} color="error">
                                        <CancelIcon />
                                        Cancel
                                    </Button>
                                    <Button variant='contained' onClick={handleCreateEvent} color="success" disabled={disable}>
                                        <CheckIcon />
                                        Create
                                    </Button>
                                </Box>
                            </Stack>
                        </Container>
                    </Popper>
                </>}
            <Stack sx={{ width: '100%' }} spacing={2}>
                {error && <Alert severity="error">{errorMessage}</Alert>}
                {success && <Alert severity="success">A new Event Type has been successfully created!</Alert>}
            </Stack>
        </>
    );
};

export default CreateEvent;
