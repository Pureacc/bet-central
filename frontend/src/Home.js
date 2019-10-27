import React from "react";
import {Typography} from "@material-ui/core";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import {connect} from "react-redux";

class Home extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <Typography variant="h4" gutterBottom component="h2">
            Home
            <Grid item xs container direction="column" spacing={16}>
                <TextField
                    id="username"
                    label="Username"
                    value={this.props.username}
                    margin="normal"
                    InputProps={{
                        readOnly: true,
                    }}
                />
                <TextField
                    id="balance"
                    label="Balance"
                    value={this.props.balance}
                    margin="normal"
                    InputProps={{
                        readOnly: true,
                    }}
                />
            </Grid>
        </Typography>
    }
}

const mapStateToProps = state => ({
   username: state.user.name,
   balance: state.user.balance
});

export default connect(mapStateToProps)(Home)