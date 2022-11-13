import axios from "axios";
import * as React from 'react';
import ListSubheader from '@mui/material/ListSubheader';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Collapse from '@mui/material/Collapse';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import EventIcon from '@mui/icons-material/Event';
import ClearAllIcon from '@mui/icons-material/ClearAll';
import PersonIcon from '@mui/icons-material/Person';
import { Alert, Button, TextField } from "@mui/material";
import { Box } from "@mui/system";
import AddIcon from '@mui/icons-material/Add';
import CancelIcon from '@mui/icons-material/Cancel';
import CheckIcon from '@mui/icons-material/Check';
import Stack from '@mui/material/Stack';

const EventTypes = () => {
    const [eventTypes, setEventTypes] = React.useState([]);
    const [open, setOpen] = React.useState(false);
    const [createForm, setCreateForm] = React.useState(false);
    const [eventName, setEventName] = React.useState("");
    const [eventDescription, setEventDescription] = React.useState("");
    const [ageRequirement, setAgeRequirement] = React.useState("");
    const [disable, setDisable] = React.useState(true);
    const [refresh, setRefresh] = React.useState(false);
    const [error, setError] = React.useState(false);
    const [errorMessage, setErrorMessage] = React.useState("");
    const [success, setSuccess] = React.useState(false);

    React.useEffect(() => {
        axios.get("event-types").then((res) => {
            setEventTypes(res.data);
        });
    }, [refresh]);

    React.useEffect(() => {
        setOpen(false);
        setCreateForm(false);
        setDisable(true);
    }, [refresh])

    React.useEffect(() => {
        if(eventName && eventDescription && ageRequirement){
            setDisable(false);
        }
        else{
            setDisable(true);
        }
    }, [setDisable, eventName, eventDescription, ageRequirement]);

    const handleClickExpand = () => {
        setOpen(!open);
    };

    const handleClickCreateForm = () => {
        setSuccess(false);
        setCreateForm(true);
    };

    const handleEventNameChange = (event) => {
        setError(false);
        setEventName(event.target.value);
    };

    const handleEventDescriptionChange = (event) => {
        setError(false);
        setEventDescription(event.target.value);
    };

    const handleAgeRequirementChange = (event) => {
        setError(false);
        setAgeRequirement(event.target.value);
    };

    const handleCancelCreateForm = () => {
        setCreateForm(false);
    };

    const handleCreateEventType = React.useCallback(() => {
        if(isNaN(ageRequirement)){
            setError(true);
            setErrorMessage("Age Requirement Must Be A Number");
        }
        else if(!isNaN(eventName)){
            setError(true);
            setErrorMessage("Event Type Name Must Be A String");
        }
        else if(!isNaN(eventDescription)){
            setError(true);
            setErrorMessage("Event Type Description Must Be A String");
        }
        else{
            axios.post("event-types", {
                name: eventName,
                description: eventDescription,
                ageRequirement: parseInt(ageRequirement)
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
    }, [ageRequirement, eventDescription, eventName]);

    return (
        <>
        <h1>Event Types</h1> 
        <List
        sx={{ width: '100%', maxWidth: 2000, bgcolor: 'background.paper' }}
        component="nav"
        aria-labelledby="Event Types"
        subheader={
            <ListSubheader component="div" id="Event Types">
            Event Types
            </ListSubheader>
        }
        >
            {eventTypes.map((eventType) => {
                return (
                    <>
                    <ListItemButton onClick={handleClickExpand}>
                        <ListItemIcon>
                        <EventIcon color="primary"/>
                        </ListItemIcon>
                        <ListItemText primary={eventType.name} />
                        {open ? <ExpandLess /> : <ExpandMore />}
                    </ListItemButton>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <List component="div" disablePadding>
                        <ListItemButton sx={{ pl: 4 }}>
                            <ListItemIcon>
                            <ClearAllIcon />
                            </ListItemIcon>
                            <ListItemText secondary={eventType.description} primary="Description" />
                        </ListItemButton>
                        <ListItemButton sx={{ pl: 4 }}>
                            <ListItemIcon>
                            <PersonIcon />
                            </ListItemIcon>
                            <ListItemText secondary={eventType.ageRequirement} primary="Age Requirement"/>
                        </ListItemButton>
                        </List>
                    </Collapse>
                    </>
                );
            })}
        </List>
        <Box textAlign='right'>
            <Button variant='contained' onClick={handleClickCreateForm}>
                <AddIcon />
                Add
            </Button>
        </Box>
        { createForm && 
        <>
            <Box textAlign='center'>
            <TextField
            required
            id="outlined-required"
            label="Required"
            helperText="Event Type Name"
            onChange={handleEventNameChange}
            />
            <TextField
            required
            id="outlined-required"
            label="Required"
            helperText="Description"
            onChange={handleEventDescriptionChange}
            />
            <TextField
            required
            id="outlined-required"
            label="Required"
            helperText="Age Requirement"
            onChange={handleAgeRequirementChange}
            />
            </Box>
            <Box textAlign='center'>
             <Button variant='contained' onClick={handleCancelCreateForm} color="error">
                <CancelIcon />
                Cancel
            </Button>
            <Button variant='contained' onClick={handleCreateEventType} color="success" disabled={disable}>
                <CheckIcon />
                Create
            </Button>
        </Box>
        </>}
        <Stack sx={{ width: '100%' }} spacing={2}>
            {error && <Alert severity="error">{errorMessage}</Alert>}
            {success && <Alert severity="success">A new Event Type has been successfully created!</Alert>}
        </Stack>
        </>
    );
  };
  export default EventTypes;
  