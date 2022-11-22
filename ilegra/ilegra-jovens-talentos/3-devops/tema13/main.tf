terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }
}

variable "aws_access_key" {
  type      = string
  sensitive = true
}

variable "aws_secret_key" {
  type      = string
  sensitive = true
}

variable "aws_key_name" {
  type = string
}

provider "aws" {
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
  region     = "us-east-1"
}

data "aws_ami" "go-calculator-ami" {
  most_recent = true
  owners      = ["self"]
  tags        = {
    Name             = "ami-tema13-brazdore"
    Owner            = "brazdore",
    Project          = "tema13",
    EC2_ECONOMIZATOR = "TRUE",
    CustomerID       = "ILEGRA"
  }
}

data "aws_security_group" "default" {
  id = "sg-812ea5f2"
}

data "aws_vpc" "default-vpc-ilegra-poc" {
  id = "vpc-fb859c82"
}

resource "aws_instance" "go-calculator-instance" {
  ami             = data.aws_ami.go-calculator-ami.id
  instance_type   = "t2.micro"
  key_name        = var.aws_key_name
  security_groups = [data.aws_security_group.default.name]
  tags            = {
    Name             = "go-calculator"
    Owner            = "brazdore",
    Project          = "tema13",
    EC2_ECONOMIZATOR = "TRUE",
    CustomerID       = "ILEGRA"
  }
}

resource "aws_launch_configuration" "go-calculator-lc" {
  key_name        = var.aws_key_name
  name_prefix     = "lc-tema13-brazdore-"
  image_id        = data.aws_ami.go-calculator-ami.image_id
  instance_type   = "t2.micro"
  security_groups = [data.aws_security_group.default.id]

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_elb" "go-calculator-elb" {
  name                      = "go-calculator-elb"
  availability_zones        = ["us-east-1a", "us-east-1b", "us-east-1c", "us-east-1d", "us-east-1e", "us-east-1f"]
  instances                 = [aws_instance.go-calculator-instance.id]
  security_groups           = [data.aws_security_group.default.id]
  cross_zone_load_balancing = true

  listener {
    instance_port     = 8080
    instance_protocol = "http"
    lb_port           = 8080
    lb_protocol       = "http"
  }

  health_check {
    healthy_threshold   = 2
    unhealthy_threshold = 2
    target              = "HTTP:8080/headers"
    interval            = 30
    timeout             = 3
  }

  tags = {
    Name             = "go-calculator-elb",
    Owner            = "brazdore",
    Project          = "tema13",
    EC2_ECONOMIZATOR = "TRUE",
    CustomerID       = "ILEGRA"
  }
}

resource "aws_autoscaling_group" "go-calculator-asg" {
  name                 = "asg-tema13-brazdore"
  availability_zones   = ["us-east-1a", "us-east-1b", "us-east-1c", "us-east-1d", "us-east-1e", "us-east-1f"]
  launch_configuration = aws_launch_configuration.go-calculator-lc.name
  load_balancers       = [aws_elb.go-calculator-elb.name]
  desired_capacity     = 1
  min_size             = 1
  max_size             = 2
  capacity_rebalance   = true

  lifecycle {
    create_before_destroy = true
  }

  tag {
    key                 = "Name"
    value               = "go-calculator"
    propagate_at_launch = true
  }

  tag {
    key                 = "Owner"
    value               = "brazdore"
    propagate_at_launch = true
  }

  tag {
    key                 = "Project"
    value               = "tema13"
    propagate_at_launch = true
  }

  tag {
    key                 = "EC2_ECONOMIZATOR"
    value               = "TRUE"
    propagate_at_launch = true
  }

  tag {
    key                 = "CustomerID"
    value               = "ILEGRA"
    propagate_at_launch = true
  }
}
