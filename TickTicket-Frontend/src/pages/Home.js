import { Container, Stack } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import EventCard from "../components/EventCard.js";

const Home = () => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    axios.get("events").then((res) => {
      setEvents(res.data);
    });
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
    </Container>
  );
};
export default Home;
