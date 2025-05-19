import React, { useState, useEffect } from 'react';
import {Table, Button, Space, Modal, Form, Input, message, Spin} from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import axios from 'axios';
import Column from "antd/es/table/Column";

const UserList = ({ currentUser, onUserUpdate }) => {
    const [users, setUsers] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [editingUser, setEditingUser] = useState(null);
    const [form] = Form.useForm();
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        setLoading(true);
        try {
            const response = await axios.get(`${process.env.REACT_APP_API_URL}/users`);
            setUsers(response.data);
        } catch (error) {
            message.error('Failed to fetch users');
        } finally {
            setLoading(false);
        }
    };

    const handleCreateOrUpdate = async (values) => {
        try {
            if (editingUser) {
                const updateData = {};
                if (values.name !== editingUser.name) updateData.name = values.name;
                if (values.email !== editingUser.email) updateData.email = values.email;

                if (values.password && values.password.trim() !== '') {
                    updateData.password = values.password;
                }

                const response = await axios.patch(
                    `${process.env.REACT_APP_API_URL}/users/${editingUser.id}`,
                    updateData
                );

                fetchUsers();

                if (currentUser && currentUser.id === editingUser.id) {
                    onUserUpdate(response.data);
                    message.success('Profile updated successfully!');
                } else {
                    message.success('User updated successfully');
                }
            } else {
                await axios.post(`${process.env.REACT_APP_API_URL}/users`, values);
                message.success('User created successfully');
                fetchUsers();
            }

            setIsModalVisible(false);
        } catch (error) {
            if (error.response?.status === 400 && error.response.data) {
                const errorMessages = Object.entries(error.response.data)
                    .map(([field, message]) => `${message}`)
                    .join('\n');

                message.error({
                    content: (
                        <div style={{whiteSpace: 'pre-line'}}>
                            {errorMessages}
                        </div>
                    ),
                    duration: 5,
                });
            } else if (error.response?.status === 409) {
                message.error({
                    content: error.response.data,
                    duration:5,
                });
            } else {
                message.error('Failed to save user');
            }
        }
    };

    const showModal = (user = null) => {
        setEditingUser(user);
        form.resetFields();
        if (user) {
            form.setFieldsValue({
                name: user.name,
                email: user.email,
                password: ''
            });
        }
        setIsModalVisible(true);
    };

    const handleDelete = async (id) => {
        Modal.confirm({
                title: 'Delete User',
                content: 'Are you sure you want to delete this user?',
                okText: 'Delete',
                okType: 'danger',
                cancelText: 'Cancel',
                onOk: async () => {
                    try {
                        await axios.delete(`${process.env.REACT_APP_API_URL}/users/${id}`);
                        message.success('User deleted successfully');
                        fetchUsers();
                    } catch (error) {
                        message.error('Failed to delete user');
                    }
                }
            }
        )
    };

    return (
        <Spin spinning={loading} tip="Loading...">
            <span className="name-text">Все отзывы</span>
            <div className="container">
                <div className="actions">
                    <Button type="primary"
                            icon={<PlusOutlined/>}
                            onClick={() => showModal()}
                            className="add-button">
                        Добавить пользователя
                    </Button>
                </div>

                <Table dataSource={users} rowKey="id">
                    <Column title="Имя пользователя" dataIndex="name" key="name"/>
                    <Column title="Email" dataIndex="email" key="email"/>
                    <Column
                        title="Действия"
                        key="action"
                        render={(_, user) => (
                            <Space size="middle">
                                <Button
                                    type="link"
                                    icon={<EditOutlined/>}
                                    onClick={() => showModal(user)}
                                />
                                <Button
                                    type="link"
                                    icon={<DeleteOutlined/>}
                                    onClick={() => handleDelete(user.id)}
                                    danger
                                />
                            </Space>
                        )}
                    />
                </Table>

                <Modal
                    title={editingUser ? "Edit User" : "Create User"}
                    visible={isModalVisible}
                    onOk={() => form.submit()}
                    onCancel={() => setIsModalVisible(false)}
                >
                    <Form form={form} onFinish={handleCreateOrUpdate} layout="vertical">
                        <Form.Item
                            name="name"
                            label="Username"
                            rules={[{required: true, message: 'Please input username!'}]}

                        >
                            <Input placeholder="Enter username"/>
                        </Form.Item>
                        <Form.Item
                            name="email"
                            label="Email"
                            rules={[{required: true, type: 'email', message: 'Please input valid email!'}]}
                        >
                            <Input placeholder="Enter email"/>
                        </Form.Item>
                        <Form.Item
                            name="password"
                            label="Password"
                            rules={[
                                editingUser ? null : {required: true, message: 'Please input password!'},
                                {min: 4, message: 'Minimum 4 characters'},
                                {max: 20, message: 'Maximum 20 characters'}
                            ].filter(rule => rule !== null)}
                        >
                            <Input.Password
                                placeholder={editingUser ? "Leave empty to keep current" : "Enter password"}/>
                        </Form.Item>
                    </Form>
                </Modal>
            </div>
        </Spin>
);
};

export default UserList;