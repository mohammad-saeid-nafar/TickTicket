import axios from "axios";
import { useEffect, useState } from "react";
import { Container, Stack } from "@mui/material";
import EventCard from "../components/EventCard";
import Filters from "../components/Filters";

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

  const filterByDate = (date) => {
    axios.get(`events/date?date=${date}`).then((res) => {
      setEvents(res.data)
    });
  }

  const filterByArea = (area) => {
    axios.get(`events/address?address=${area}`).then((res) => {
      setEvents(res.data)
    });
  }

  const filterByEventType = (eventType) => {
    axios.get(`events/event-type?eventTypeName=${eventType}`).then((res) => {
      setEvents(res.data)
    });
  }

  return (
    <Container
      sx={{
        paddingTop: "5%",
      }}
    >
      <Stack spacing={2}>
      <Filters filterByCost={filterByCost} filterByDate={filterByDate} filterByArea={filterByArea} filterByEventType={filterByEventType} clearFilter={loadData}></Filters>
        {events.map((event) => {
          return <EventCard key={event.id} event={event} addReview={false} />;
        })}
      </Stack>
    </Container>
  );
};
export default Home;