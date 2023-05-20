import React, { useState } from "react";
import BackendService from "../../services/BackendService";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronLeft, faSave } from "@fortawesome/free-solid-svg-icons";
import { alertActions } from "../../utils/Rdx";
import { connect } from "react-redux";
import { Form } from "react-bootstrap";
import { useParams, useNavigate } from 'react-router-dom';

const ArtistComponent = props => {

    const [hidden, setHidden] = useState(false);
    const navigate = useNavigate();
    const [name, setName] = useState("")
    const [century, setCentury] = useState()
    const [id, setId] = useState(useParams().id)

    const updateName = (event) => {
        setName(event.target.value)
    }

    const updateCentury = (event) => {
        setCentury(event.target.value)
    }

    const onSubmit = (event) => {
        event.preventDefault();
        event.stopPropagation();
        let err = null;
        if (name === "") {
            err = "Имя должно быть указано"
        }
        let artist = { name: name, century: century, country: {"id": "20"} }
        if (parseInt(id) == -1) {
            BackendService.createArtist(artist)
                .catch(() => { })
        }
        else {
            let artist = { name: name, id: id, century: century, country: {"id": "20"} }
            BackendService.updateArtist(artist)
                .catch(() => { })
        }
        navigateToArtists()
    }

    const navigateToArtists = () => {
        navigate('/artists')
    }

    if (hidden)
        return null;
    return (
        <div className="m-4">
            <div className="row my-2 mr-0">
                <h3>Добавить художника</h3>
                <button
                    className="btn btn-outline-secondary ml-auto"
                    onClick={() => navigateToArtists()}><FontAwesomeIcon
                        icon={faChevronLeft} />{' '}Назад</button>
            </div>
            <Form onSubmit={onSubmit}>
                <Form.Group>
                    <Form.Label>Название</Form.Label>

                    <Form.Control
                        type="text"
                        placeholder="Введите имя художника"
                        onChange={updateName}
                        value={name}
                        name="name"
                        autoComplete="off" />
                    <Form.Control
                        type="int"
                        placeholder="Введите век"
                        onChange={updateCentury}
                        value={century}
                        name="name"
                        autoComplete="off" />
                </Form.Group>
                <button
                    className="btn btn-outline-secondary"
                    type="submit"><FontAwesomeIcon
                        icon={faSave} />{' '}Сохранить</button>
            </Form>
        </div>
    )

}

export default ArtistComponent;