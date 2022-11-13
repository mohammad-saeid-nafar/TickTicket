import * as React from 'react';
import axios from "axios";
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';

const CreateEvent = () => {
  const [eventTypes, setEventTypes] = React.useState([]);
  const [alignment, setAlignment] = React.useState('web');
  const [eventType, setEventType] = React.useState("");

  React.useEffect(() => {
    axios.get("event-types").then((res) => {
        setEventTypes(res.data);
    });
  }, []);

  const handleChange = (
    event,
    newAlignment,
  ) => {
    setAlignment(newAlignment);
    setEventType(event.target.value);
    // Todo remove console log line when eventType gets used
    console.log(eventType);
  };

  return(
    <>
      <h1>Create Event</h1>
      <h2>1. Select Event Type</h2>
      <ToggleButtonGroup
        color="primary"
        value={alignment}
        exclusive
        onChange={handleChange}
      >
        {eventTypes.map((eventType) => {
          return (
            <ToggleButton value={eventType.name}>{eventType.name}</ToggleButton>
          );
        })}
      </ToggleButtonGroup>
    </>
  );
};
export default CreateEvent;
