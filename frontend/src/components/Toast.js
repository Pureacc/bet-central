import React, {Component} from 'react';
import {SnackbarContentWrapper} from "./SnackbarContentWrapper";
import Snackbar from "@material-ui/core/Snackbar";

export default class Toast extends Component {
    constructor(props) {
        super(props);
        this.state = {open: false};
    }

    componentDidUpdate(prevProps) {
        const {message} = this.props;
        if (!!message && message !== prevProps.message) {
            this.setState({open: true});
        }
    }

    handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        const {onClose} = this.props;
        this.setState({open: false});
        onClose();
    };

    render() {
        const {variant, message} = this.props;

        return (
            <Snackbar
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                }}
                open={this.state.open}
                autoHideDuration={5000}
                onClose={this.handleClose}
            >
                <SnackbarContentWrapper
                    onClose={this.handleClose}
                    variant={variant}
                    message={message}
                />
            </Snackbar>
        );
    }
}