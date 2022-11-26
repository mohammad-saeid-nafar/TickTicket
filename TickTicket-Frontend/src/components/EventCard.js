import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { styled } from "@mui/material/styles";
import {
  Box,
  Card,
  CardActions,
  CardHeader,
  Collapse,
  IconButton,
  Modal,
  Typography,
} from "@mui/material";
import {
  Delete as DeleteIcon,
  Edit as EditIcon,
  ExpandMore as ExpandMoreIcon,
  RateReview as RateReviewIcon,
} from "@mui/icons-material";
import EventRating from "./EventRating";
import ReviewModal from "./ReviewModal";
import EventInformation from "./EventInformation";

const EventCard = (props) => {
  const [expanded, setExpanded] = useState(false);
  const [reviews, setReviews] = useState([]);
  const [rating, setRating] = useState(0);
  const [reviewOpen, setReviewOpen] = React.useState(false);
  const [editOpen, setEditOpen] = React.useState(false);

  const [error, setError] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState("");
  const [success, setSuccess] = React.useState(false);

  useEffect(() => {
    loadData();
    // eslint-disable-next-line
  }, []);

  const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
  })(({ theme, expand }) => ({
    transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
      duration: theme.transitions.duration.shortest,
    }),
  }));

  const loadData = async () => {
    await axios.get(`reviews/event/${props.event.id}`).then((res) => {
      setReviews(res.data);
    });
    await axios.get(`reviews/event/${props.event.id}/average`).then((res) => {
      setRating(Math.round(res.data * 10) / 10);
    });
  };

  const handleExpandClick = async () => {
    setExpanded(!expanded);
  };

  const handleReviewOpen = () => {
    setReviewOpen(true);
  };

  const handleReviewClose = () => setReviewOpen(false);

  const handleEditOpen = () => {
    setEditOpen(true);
  };

  const handleEditClose = () => setEditOpen(false);

  const createReview = async (title, description, rating) => {
    await axios.post(`reviews`, {
      title: title,
      description: description,
      rating: rating,
      eventId: props.event.id,
      userId: localStorage.getItem("userId"),
    });
    loadData();
    handleReviewClose();
  };

  const handleEditEvent = async (
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
      .patch(`events`, {
        id: props.event.id,
        name: eventName,
        description: eventDescription,
        capacity: eventCapacity,
        cost: eventCost,
        start: eventStart,
        end: eventEnd,
        address: eventAddress,
        phoneNumber: eventPhoneNumber,
        email: eventEmail,
        eventTypes: chosenEventTypes,
      })
      .then(function (response) {
        setSuccess(true);
        // handlePageChange( 1);
        loadData();
      })
      .catch(function (error) {
        setError(true);
        setErrorMessage(error.response.data.message);
      });
    props.loadData();
    handleEditClose();
  };

  const deleteEvent = async () => {
    await axios.delete(`events/${props.event.id}`);
    props.loadData();
  };

  return (
    <Card>
      <CardHeader
        action={
          <div>
            {props.addReview && (
              <IconButton
                id="basic-button"
                aria-haspopup="true"
                onClick={handleReviewOpen}
                disabled={
                  props.event.organizer.id === localStorage.getItem("userId") ||
                  reviews.some(
                    (review) =>
                      review.user.id === localStorage.getItem("userId"),
                  )
                }
              >
                <RateReviewIcon />
              </IconButton>
            )}
            {props.event.organizer.id === localStorage.getItem("userId") && (
              <>
                <IconButton
                  size="large"
                  color="inherit"
                  onClick={handleEditOpen}
                >
                  <EditIcon />
                </IconButton>
                <IconButton size="large" color="inherit" onClick={deleteEvent}>
                  <DeleteIcon />
                </IconButton>
              </>
            )}
            <Modal class open={editOpen} onClose={handleEditClose}>
              <EventInformation
                aria-labelledby="event-information-title"
                aria-describedby="event-information-description"
                handleAction={handleEditEvent}
                event={props.event}
                error={error}
                errorMessage={errorMessage}
                success={success}
              ></EventInformation>
            </Modal>
            <ReviewModal
              open={reviewOpen}
              handleClose={handleReviewClose}
              handleAction={createReview}
              aria-labelledby="modal-modal-title"
              aria-describedby="modal-modal-description"
            ></ReviewModal>
          </div>
        }
        title={
          <Box
            sx={{
              display: "flex",
            }}
          >
            <Typography>{props.event.name}</Typography>
            <EventRating
              reviews={reviews}
              rating={rating}
              loadData={loadData}
            />
          </Box>
        }
        subheader={props.event.description}
      />
      <CardActions disableSpacing>
        {reviews.length !== 0 && (
          <ExpandMore
            expand={expanded}
            onClick={handleExpandClick}
            aria-expanded={expanded}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </ExpandMore>
        )}
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <Box sx={{ p: 2 }}>
          <Typography variant="body2" color="text.secondary">
            Cost: {props.event.cost}$
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Capacity: {props.event.capacity}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Start: {props.event.eventSchedule.start.split("T")[0]}{" "}
            {props.event.eventSchedule.start.split("T")[1]}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            End: {props.event.eventSchedule.end.split("T")[0]}{" "}
            {props.event.eventSchedule.end.split("T")[1]}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Address: {props.event.address}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Phone Number: {props.event.phoneNumber}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Email: {props.event.email}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Organizer: {props.event.organizer.profile.firstName}{" "}
            {props.event.organizer.profile.lastName}
          </Typography>
        </Box>
      </Collapse>
    </Card>
  );
};
export default EventCard;
