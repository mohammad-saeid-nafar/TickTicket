import { Paper } from "@mui/material";
import { styled } from "@mui/material/styles";

const Filter = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
  ...theme.typography.body2,
  textAlign: "center",
  color: theme.palette.text.secondary,
  width: "100%",
}));

export default Filter;
