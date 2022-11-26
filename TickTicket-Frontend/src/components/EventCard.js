import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { styled } from "@mui/material/styles";
import {
  Box,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Collapse,
  IconButton,
  List,
  Menu,
  MenuItem, Modal,
  Typography,
} from "@mui/material";
import {
  Delete as DeleteIcon,
  Edit as EditIcon,
  ExpandMore as ExpandMoreIcon,
  MoreVert as MoreVertIcon,
} from "@mui/icons-material";
import Review from "./Review";
import EventRating from "./EventRating";
import ReviewModal from "./ReviewModal";
import EventInformation from "./EventInformation";

const EventCard = (props) => {
  const [expanded, setExpanded] = useState(false);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(false);
  const [rating, setRating] = useState(0);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [reviewOpen, setReviewOpen] = React.useState(false);
  const [editOpen, setEditOpen] = React.useState(false);
  const actionsOpen = Boolean(anchorEl);

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
    setLoading(true);
    await axios.get(`reviews/event/${props.event.id}`).then((res) => {
      setReviews(res.data);
      setLoading(false);
    });
    await axios.get(`reviews/event/${props.event.id}/average`).then((res) => {
      setRating(Math.round(res.data * 10) / 10);
    });

  };

  const handleExpandClick = async () => {
    setExpanded(!expanded);
  };

  const handleActionsClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleActionsClose = () => {
    setAnchorEl(null);
  };

  const handleReviewOpen = () => {
    handleActionsClose();
    setReviewOpen(true);
  };

  const handleReviewClose = () => setReviewOpen(false);

  const handleEditOpen = () => {
    handleActionsClose();
    setEditOpen(true);
    console.log("set true");
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

  const handleEditEvent = async (eventName, eventDescription, eventCapacity, eventCost, eventStart, eventEnd, eventAddress, eventPhoneNumber, eventEmail, chosenEventTypes) => {
    await axios.patch(`events`, {
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
    }) .then(function (response) {
      setSuccess(true);
      // handlePageChange( 1);
      loadData();
    }).catch(function (error) {
      setError(true);
      setErrorMessage(error.response.data.message);
    });
    props.loadData();
    handleEditClose();
  }

  const deleteEvent = async () => {
    await axios.delete(`events/${props.event.id}`);
    props.loadData();
  }

  return (
    <Card>
      <CardHeader
        action={
          <div>
            {(props.addReview &&
              props.event.organizer.id !== localStorage.getItem("userId") &&
              reviews.every(
                (review) => review.user.id !== localStorage.getItem("userId"),
              ) && (
                <IconButton
                  id="basic-button"
                  aria-controls={actionsOpen ? "basic-menu" : undefined}
                  aria-haspopup="true"
                  aria-expanded={actionsOpen ? "true" : undefined}
                  onClick={handleActionsClick}
                >
                  <MoreVertIcon />
                </IconButton>
              )) || ((props.event.organizer.id === localStorage.getItem("userId") && (
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
            )))}
            <Modal
                class
                open={editOpen}
                onClose={handleEditClose}>
                <EventInformation
                  aria-labelledby="event-information-title"
                  aria-describedby="event-information-description"
                  handleAction={handleEditEvent}
                  event={props.event}
                  error={error}
                  errorMessage={errorMessage}
                  success={success}
                  >
                </EventInformation>
            </Modal>
            <Menu
              id="basic-menu"
              anchorEl={anchorEl}
              open={actionsOpen}
              onClose={handleActionsClose}
              MenuListProps={{
                "aria-labelledby": "basic-button",
              }}
            >
              <MenuItem onClick={handleReviewOpen}>Add Review</MenuItem>
            </Menu>
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
            <EventRating reviews={reviews} rating={rating} />
          </Box>
        }
        subheader={props.event.address}
      />
      <CardContent>
        <Typography variant="body2" color="text.secondary">
          {props.event.description}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Cost: {props.event.cost}$
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        {reviews.length !== 0 && (
          <ExpandMore
            expand={expanded}
            disabled={loading || reviews.length === 0}
            onClick={handleExpandClick}
            aria-expanded={expanded}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </ExpandMore>
        )}
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <List sx={{ width: "100%", bgcolor: "background.paper" }}>
          {reviews.map((review) => {
            return (
              <Review key={review.id} review={review} loadData={loadData} />
            );
          })}
        </List>
      </Collapse>
    </Card>
  );
};
export default EventCard;
