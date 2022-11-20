import { useState } from "react";
import {
  Card,
  Box,
  Typography,
  Modal,
  Rating,
  TextField,
  Button,
} from "@mui/material";

const ReviewModal = (props) => {
  const [rating, setRating] = useState(props.review ? props.review.rating : 0);
  const [title, setTitle] = useState(props.review ? props.review.title : "");
  const [description, setDescription] = useState(
    props.review ? props.review.description : "",
  );

  const modalStyle = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: "50%",
    bgcolor: "background.paper",
    boxShadow: 24,
    p: 4,
  };

  return (
    <Modal
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Card sx={modalStyle}>
        <Box
          component="form"
          sx={{
            "& .MuiTextField-root": { m: 1, width: "25ch" },
          }}
          noValidate
          autoComplete="off"
        >
          <Typography component="legend">Rating</Typography>
          <Rating
            value={rating}
            onChange={(event, newValue) => {
              setRating(newValue);
            }}
          />
          <div>
            <TextField
              required
              id="review-title"
              label="Title"
              value={title}
              onChange={(event) => setTitle(event.target.value)}
            />
            <TextField
              required
              multiline
              id="review-description"
              label="Description"
              helperText="Please provide a description of your experience."
              value={description}
              onChange={(event) => setDescription(event.target.value)}
            />
          </div>
          <Button
            variant="outlined"
            onClick={() => props.handleAction(title, description, rating)}
          >
            {props.review ? "Edit Review" : "Add Review"}
          </Button>
        </Box>
      </Card>
    </Modal>
  );
};

export default ReviewModal;
