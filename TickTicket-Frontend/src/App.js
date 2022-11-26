import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Signin from "./pages/Signin";
import Signup from "./pages/Signup";
import Profile from "./pages/Profile";
import Events from "./pages/Events";
import CreateEvent from "./pages/CreateEvent";
import NavBar from "./pages/NavBar";
import EventTypes from "./pages/EventTypes";
import MyEvents from "./pages/MyEvents"

function App() {
  return (
    <BrowserRouter>
      <div>
        <NavBar/>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="signin" element={<Signin />} />
            <Route path="signup" element={<Signup />} />
            <Route path="profile" element={<Profile />} />
            <Route path="events" element={<Events />} />
            <Route path="event_types" element={<EventTypes />} />
            <Route path="create_event" element={<CreateEvent />} />
            <Route path="my_tickets" element={<MyEvents />} />
          </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;