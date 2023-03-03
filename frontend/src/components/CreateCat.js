import React, {Component} from "react";
import {
    Alert,
    Box,
    Button,
    Collapse,
    FormControl,
    InputLabel,
    MenuItem,
    Modal,
    OutlinedInput,
    Select,
    Stack,
    TextField, Typography
} from "@mui/material";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

const Colors = {
    WHITE: 'White',
    BLACK: 'Black',
    BLACK_WHITE: 'Black & White',
    RED: 'Red',
    BLACK_RED: 'Black & Red',
    RED_WHITE: 'Red & White',
    RED_WHITE_BLACK: 'Red & White & Black',
    BLUE: 'Blue',
    FAWN: 'Fawn',
    CREAM: 'Cream'
};

class CreateCat extends Component {

    emptyCatInfo = {
        name: '',
        color: '',
        tailLength: '',
        whiskersLength: ''
    };

    errorDefault = {
        errorName: '',
        errorColor: '',
        errorTailLength: '',
        errorWhiskersLength: ''
    }

    constructor(props) {
        super(props);
        this.state = {
            cat: this.emptyCatInfo,
            openModal: false,
            disableSave: true,
            error: this.errorDefault,
            showAlert: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.handleClose = this.handleClose.bind(this);
        this.handleOpen = this.handleOpen.bind(this);
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {cat} = this.state;

        await fetch('/cat-info/v1/cat', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(cat),
        }).then((res) => this.setState({showAlert: res.status === 409}));
        setTimeout(function () {
            window.location.reload()
        }, 3000);
    }

    validate(event) {
        const name = event.target.name;
        const value = event.target.value;
        switch (name) {
            case 'name':
                if (value !== '') {
                    this.state.error.errorName = '';
                } else {
                    this.state.error.errorName = 'Name is required field';
                }
                break;
            case 'color':
                if (value !== '') {
                    this.state.error.errorColor = '';
                } else {
                    this.state.error.errorColor = 'Color is required field';
                }
                break;
            case 'tailLength':
                if (/^\d$|^[1-9]\d$|^(100)$/.test(value)) {
                    this.state.error.errorTailLength = '';
                } else {
                    this.state.error.errorTailLength = 'Tail length must be between 0 and 100';
                }
                break;
            case 'whiskersLength':
                if (/^(\d|[1-4]\d?|50)$/.test(value)) {
                    this.state.error.errorWhiskersLength = '';
                } else {
                    this.state.error.errorWhiskersLength = 'Whiskers length must be between 0 and 50';
                }
                break;
        }

    }

    handleChange(event) {
        this.setState((state) => state.cat[event.target.name] = event.target.value);
        this.validate(event);
        this.state.disableSave = !(Object.values(this.state.error).every(x => x === '') && Object.values(this.state.cat).every(x => x !== ''));
    }

    handleOpen() {
        this.setState((state) => state.openModal = true);
    }

    handleClose() {
        this.setState((state) => state.openModal = false);
    }

    render() {
        const {cat, error} = this.state;

        return (
            <div>
                <Button onClick={this.handleOpen} variant="outlined">Create Cat</Button>
                <Modal
                    open={this.state.openModal}
                    onClose={this.handleClose}
                >
                    <Box sx={style}>
                        <Typography id="modal-modal-title" variant="h6" component="h2">
                            Add New Cat
                        </Typography>
                        <Collapse in={this.state.showAlert}>
                            <Alert severity="error">Name {cat.name} is exist in DB</Alert>
                        </Collapse>
                        <Stack component="form" sx={{width: '200', m: 2}} spacing={2}>
                            <TextField name="name" label="Name" variant="outlined" onChange={this.handleChange}
                                       helperText={error.errorName}
                                       error={error.errorName !== ''}
                                       value={cat.name}/>
                            <FormControl>
                                <InputLabel id="demo-multiple-name-label">Color</InputLabel>
                                <Select
                                    name="color"
                                    value={cat.color}
                                    helperText={error.errorColor}
                                    error={error.errorColor !== ''}
                                    onChange={this.handleChange}
                                    input={<OutlinedInput label="Color"/>}>
                                    {Object.entries(Colors).map(([key, value]) => (
                                        <MenuItem
                                            key={key}
                                            value={value}>
                                            {value}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                            <TextField name="tailLength" label="Tail Length" variant="outlined"
                                       onChange={this.handleChange}
                                       helperText={error.errorTailLength}
                                       error={error.errorTailLength !== ''}
                                       value={cat.tailLength}/>
                            <TextField name="whiskersLength" label="Whiskers Length" variant="outlined"
                                       onChange={this.handleChange}
                                       helperText={error.errorWhiskersLength}
                                       error={error.errorWhiskersLength !== ''}
                                       value={cat.whiskersLength}/>
                            <Stack direction="row" spacing={30}>
                                <Button variant="contained" color="success" disabled={this.state.disableSave}
                                        onClick={this.handleSubmit}>Save</Button>
                                <Button variant="contained" onClick={this.handleClose}>Close</Button>
                            </Stack>
                        </Stack>
                    </Box>
                </Modal>
            </div>
        );
    }
}

export default CreateCat;