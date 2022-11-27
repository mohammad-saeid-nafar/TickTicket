import { Alert, Container, Stack } from "@mui/material";
import axios from "axios";
import EventCard from "../components/EventCard";
import * as React from "react";
import Tab from "@mui/material/Tab";
import Tabs from "@mui/material/Tabs";
import TabPanel from "../components/TabPanel";
import EventInformation from "../components/EventInformation";
import { useState } from "react";

const CreateEvent = () => {
  const [error, setError] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState("");
  const [success, setSuccess] = React.useState(false);
  const [events, setEvents] = useState([]);
  const [tab, setTab] = React.useState(0);

  const handlePageChange = (event, newValue) => {
    setSuccess(false);
    setError(false);
    setTab(newValue);
  };

  React.useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    await axios
      .get(`events/organizer/${localStorage.getItem("username")}`)
      .then((res) => {
        setEvents(res.data);
      });
  };

  const handleCreateEvent = async (
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
  ) => {
    await axios
      .post("events", {
        name: eventName,
        description: eventDescription,
        capacity: parseInt(eventCapacity),
        cost: parseFloat(eventCost),
        address: eventAddress,
        email: eventEmail,
        phoneNumber: eventPhoneNumber,
        organizerId: localStorage.getItem("userId"),
        start: eventStart,
        end: eventEnd,
        eventTypeIds: chosenEventTypes,
      })
      .then(function (response) {
        setSuccess(true);
        setError(false);
        loadData();
        setTab(0);
      })
      .catch(function (error) {
        setError(true);
        setSuccess(false);
        setErrorMessage(error.response.data.message);
      });
  };

  return (
    <>
      <h1>Events</h1>
      <Container
        sx={{
          paddingTop: "3%",
        }}
      >
        <Tabs value={tab} onChange={handlePageChange} centered>
          <Tab label="My Events" />
          <Tab label="Create New Event" />
        </Tabs>
        <TabPanel value={tab} index={0}>
          <Stack spacing={2}>
            {events.map((event) => {
              return (
                <EventCard key={event.id} event={event} loadData={loadData} />
              );
            })}
          </Stack>
        </TabPanel>
        <TabPanel value={tab} index={1}>
          <EventInformation handleAction={handleCreateEvent}></EventInformation>
          <Stack sx={{ width: "100%" }} spacing={2}>
            {error && <Alert severity="error">{errorMessage}</Alert>}
            {success && (
              <Alert severity="success">
                A new Event has been successfully created!
              </Alert>
            )}
          </Stack>
        </TabPanel>
      </Container>
    </>
  );
};

export default CreateEvent;
