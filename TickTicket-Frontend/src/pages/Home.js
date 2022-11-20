import { Container, Stack } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import EventCard from "../components/EventCard";

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
          return <EventCard key={event.id} event={event} addReview={false}/>;
        })}
      </Stack>
    </Container>
  );
};
export default Home;
