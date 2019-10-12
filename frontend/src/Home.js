import React from "react";
import {Typography} from "@material-ui/core";
import Axios from "axios";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "username",
            balance: 0
        }
    }

    componentDidMount() {
        Axios.get(`/api/user`, {params: {userId: this.props.userId}})
            .then((res) => {
                this.setState({username: res.data.username, balance: res.data.balance});
            });
    }

    render() {
        return <Typography variant="h4" gutterBottom component="h2">
            Home
            <Grid item xs container direction="column" spacing={16}>
                <TextField
                    id="username"
                    label="Username"
                    value={this.state.username}
                    margin="normal"
                    InputProps={{
                        readOnly: true,
                    }}
                />
                <TextField
                    id="balance"
                    label="Balance"
                    value={this.state.balance}
                    margin="normal"
                    InputProps={{
                        readOnly: true,
                    }}
                />
            </Grid>
        </Typography>
    }
}

export default Home