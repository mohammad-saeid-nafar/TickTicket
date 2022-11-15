import { Container, Stack } from "@mui/material"; //Container, 
import axios from "axios";
import { useEffect, useState } from "react";
import EventCard from "../components/EventCard";
import * as React from 'react';
import { Button } from "@mui/material";


const Home = () => {
  const [events, setEvents] = useState([]);
  // const [disable, setDisable] = React.useState(true);

  useEffect(() => {
    axios.get("events/events-by-date").then((res) => {
      setEvents(res.data);
    });
  }, []);

  const handleEventsByDate = React.useCallback(() => {

  }, []);

  return (
    <Container
      sx={{
        paddingTop: "5%",
      }}
    >
      <Stack spacing={2}>
        {events.map((event) => {
          return <EventCard key={event.id} event={event} />;
        })}
      </Stack>
       <Button variant='contained' onClick={handleEventsByDate} color="primary" >
         Sign Up
      </Button>
    </Container>
  );
};
export default Home;
