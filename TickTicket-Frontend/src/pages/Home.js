import axios from "axios";
import { useEffect, useState } from "react";

const Home = () => {
  const [events, setEvents] = useState([]);
  const AXIOS = axios.create({
    baseURL: "https://tickticket-backend.herokuapp.com/api/v1/"
})
  useEffect(() => {
    AXIOS.get("events")
      .then((res) => {
        console.log(res.data)
        setEvents(res.data);
      });
  }, []);
  return (
    <div>
      <h1>Home</h1>
      {events.map((item, i) => {
        return (
          <div key={i}>
            <p>{item?.name}</p>
          </div>
        );
      })}
    </div>
  );
};
export default Home;
