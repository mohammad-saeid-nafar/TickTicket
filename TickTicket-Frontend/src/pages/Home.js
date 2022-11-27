import axios from "axios";
import { useEffect, useState } from "react";
import { Container, Stack } from "@mui/material";
import EventCard from "../components/EventCard";
import Filter from "../components/Filter";

const Home = () => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = () => {
    axios.get("events").then((res) => {
      setEvents(res.data);
    });
  }

  const filterByCost = (costRange) => {
    axios.get(`events/cost?minCost=${costRange[0]}&maxCost=${costRange[1]}`).then((res) => {
      setEvents(res.data)
    });
  }

  return (
    <Container
      sx={{
        paddingTop: "5%",
        paddingBottom: "5%",
      }}
    >
      <Stack spacing={2}>
      <Filter filterByCost={filterByCost} clearFilter={loadData}></Filter>
        {events.map((event) => {
          return <EventCard key={event.id} event={event} addReview={false} />;
        })}
      </Stack>
    </Container>
  );
};
export default Home;
